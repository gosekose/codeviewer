package codeview.main.businessservice.problem.presentation.controller.admin;

import codeview.main.businessservice.problem.application.ProblemCreateService;
import codeview.main.businessservice.problem.application.ProblemScoreService;
import codeview.main.businessservice.problem.application.ProblemService;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problem.presentation.dao.ProblemIoFileDao;
import codeview.main.businessservice.problem.presentation.dao.ProblemServerDao;
import codeview.main.businessservice.problem.presentation.dto.IoFileDataDto;
import codeview.main.businessservice.problem.presentation.dto.ProblemCreatedResultDto;
import codeview.main.businessservice.problem.presentation.dto.ServerIoFilePathDto;
import codeview.main.businessservice.problem.presentation.error.BindingError;
import codeview.main.serverconnect.application.service.HttpConnectionService;
import codeview.main.serverconnect.presentation.dto.ServerIoFileDemoTestResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups/admin")
@Slf4j
@RequiredArgsConstructor
public class ProblemAdminRestCreateController {

    private final ProblemService problemService;

    private final ProblemCreateService problemCreateService;

    private final HttpConnectionService httpConnectionService;
    private final ProblemScoreService problemScoreService;
    
    @Transactional
    @PostMapping("/{groupId}/problems/new")
    public ResponseEntity<ProblemCreatedResultDto> postCreateProblem2(
            @PathVariable("groupId") Integer groupId,
            @Validated @ModelAttribute ProblemCreateDao problemCreateDao,
            BindingResult bindingResult) throws IOException, NoSuchAlgorithmException {

        if (problemCreateDao.getProblemFile() == null || problemCreateDao.getProblemFile().isEmpty() ||
            problemCreateDao.getIoZipFile() == null || problemCreateDao.getIoZipFile().isEmpty()
        ) {
            return new ResponseEntity<>(ProblemCreatedResultDto.builder().message("파일 업로드는 반드시 필요합니다.").build(), HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return new ResponseEntity<>(BindingError.getProblemCreatedResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Problem problem = problemCreateService.getProblem(groupId, problemCreateDao);
        problemScoreService.saveByCreateDao(problemCreateDao, problem);
        Long problemId = problemService.save(problem);

        if (problemCreateDao.getPreFilePath() != null && !problemCreateDao.getPreFilePath().equals("")) {
            problemCreateService.deletePreFile(problemCreateDao.getPreFilePath());
        }

        problemCreateService.saveProblemData(problemCreateDao, problem);

        return new ResponseEntity<ProblemCreatedResultDto>(ProblemCreatedResultDto
                .builder()
                .problemId(problemId)
                .build(), HttpStatus.OK);
    }



    @PostMapping("/{groupId}/problems/upload/io")
    public ResponseEntity<IoFileDataDto> uploadIoFile(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemIoFileDao problemIoFileDao) throws IOException {

        if (problemIoFileDao == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (problemIoFileDao.getPreFilePath() != null && !problemIoFileDao.getPreFilePath().equals("")) {
            problemCreateService.deletePreFile(problemIoFileDao.getPreFilePath());
        }

        IoFileDataDto ioFileDataDto = problemCreateService.convertIoZip(groupId, problemIoFileDao.getIoZipFile(), String.valueOf(UUID.randomUUID()));

        if (ioFileDataDto == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(ioFileDataDto, HttpStatus.OK);
    }

    @PostMapping("/{groupId}/problems/upload/server")
    public ResponseEntity<ServerIoFilePathDto> problemDockerTest(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemServerDao problemServerDao) throws IOException {

        if (problemServerDao == null || problemServerDao.getProblemFile() == null || problemServerDao.getIoZipFile() == null || problemServerDao.getScores() == null) {
            log.info("not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }


        ServerIoFilePathDto serverIoFilePathDto = problemCreateService.convertServerFile(groupId, problemServerDao, String.valueOf(UUID.randomUUID()));
        ServerIoFileDemoTestResDto serverIoFilePathDtoReq = httpConnectionService.requestProblemCreateTest(serverIoFilePathDto);

        log.info("mainFilePath = {}", serverIoFilePathDto.getMainFilePath());

        return new ResponseEntity(serverIoFilePathDtoReq, HttpStatus.OK);
    }

}

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

//    @PostMapping("/{groupId}/problems/new/3")
//    public ResponseEntity<ProblemCreatedResultDto> postCreateProblem(
//            @PathVariable("groupId") Integer groupId,
//            @ModelAttribute ProblemCreateDao problemCreateDao) throws IOException {
//
//        Problem problem = problemCreateService.getProblem(groupId, problemCreateDao);
//        Long problemId = problemService.save(problem);
//        problemCreateService.saveProblemData(problemCreateDao, problem);
//
//        return new ResponseEntity<ProblemCreatedResultDto>(ProblemCreatedResultDto
//                .builder()
//                .problemId(problemId)
//                .build(), HttpStatus.OK);
//    }

    @Transactional
    @PostMapping("/{groupId}/problems/new")
    public ResponseEntity<ProblemCreatedResultDto> postCreateProblem2(
            @PathVariable("groupId") Integer groupId,
            @Validated @ModelAttribute ProblemCreateDao problemCreateDao,
            BindingResult bindingResult) throws IOException {

        if (problemCreateDao.getProblemFile() == null || problemCreateDao.getProblemFile().isEmpty() ||
            problemCreateDao.getIoZipFile() == null || problemCreateDao.getIoZipFile().isEmpty()
        ) {
            return new ResponseEntity<>(ProblemCreatedResultDto
                    .builder()
                    .message("파일 업로드는 반드시 필요합니다.")
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            
            return new ResponseEntity<>(ProblemCreatedResultDto
                    .builder()
                    .message("bindingError")
                    .problemType(bindingResult.getFieldValue("problemType"))
                    .problemName(bindingResult.getFieldValue("problemName"))
                    .openTime(bindingResult.getFieldValue("openTime"))
                    .closedTime(bindingResult.getFieldValue("closedTime"))
                    .problemDifficulty(bindingResult.getFieldValue("problemDifficulty"))
                    .descriptions(bindingResult.getFieldValue("descriptions"))
                    .inputs(bindingResult.getFieldValue("inputs"))
                    .outputs(bindingResult.getFieldValue("outputs"))
                    .scores(bindingResult.getFieldValue("scores"))
                    .totalScore(bindingResult.getFieldValue("totalScore"))
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }


        Problem problem = problemCreateService.getProblem(groupId, problemCreateDao);
        problemScoreService.saveByCreateDao(problemCreateDao, problem);
        Long problemId = problemService.save(problem);

        if (problemCreateDao.getPreFilePath() != null && !problemCreateDao.getPreFilePath().equals("")) {
            log.info("problemIoFilDao.getPreFilePath = {}", problemCreateDao.getPreFilePath());
            problemCreateService.deletePreFile(problemCreateDao.getPreFilePath());
            log.info("delete complete");
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

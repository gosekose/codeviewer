package codeview.main.businessservice.problem.presentation.controller.admin;

import codeview.main.businessservice.problem.application.ProblemCreateService;
import codeview.main.businessservice.problem.application.ProblemService;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problem.presentation.dao.ProblemIoFileDao;
import codeview.main.businessservice.problem.presentation.dao.ProblemServerDao;
import codeview.main.businessservice.problem.presentation.dto.IoFilePathDto;
import codeview.main.businessservice.problem.presentation.dto.ProblemIdDto;
import codeview.main.businessservice.problem.presentation.dto.ServerIoFilePathDto;
import codeview.main.serverconnect.application.service.HttpConnectionService;
import codeview.main.serverconnect.presentation.dto.ServerIoFilePathResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @PostMapping("/{groupId}/problems/new/3")
//    public ResponseEntity<ProblemIdDto> postCreateProblem(
//            @PathVariable("groupId") Integer groupId,
//            @ModelAttribute ProblemCreateDao problemCreateDao) throws IOException {
//
//        Problem problem = problemCreateService.getProblem(groupId, problemCreateDao);
//        Long problemId = problemService.save(problem);
//        problemCreateService.saveProblemData(problemCreateDao, problem);
//
//        return new ResponseEntity<ProblemIdDto>(ProblemIdDto
//                .builder()
//                .problemId(problemId)
//                .build(), HttpStatus.OK);
//    }

    @PostMapping("/{groupId}/problems/new")
    public ResponseEntity<ProblemIdDto> postCreateProblem2(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemCreateDao problemCreateDao) throws IOException {

        Problem problem = problemCreateService.getProblem(groupId, problemCreateDao);
        Long problemId = problemService.save(problem);


        if (problemCreateDao.getPreFilePath() != null && !problemCreateDao.getPreFilePath().equals("")) {
            log.info("problemIoFilDao.getPreFilePath = {}", problemCreateDao.getPreFilePath());
            problemCreateService.deletePreFile(problemCreateDao.getPreFilePath());
            log.info("delete complete");
        }


        problemCreateService.saveProblemData(problemCreateDao, problem);

        return new ResponseEntity<ProblemIdDto>(ProblemIdDto
                .builder()
                .problemId(problemId)
                .build(), HttpStatus.OK);
    }



    @PostMapping("/{groupId}/problems/upload/io")
    public ResponseEntity<IoFilePathDto> uploadIoFile(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemIoFileDao problemIoFileDao) throws IOException {


        log.info("problemIoFileDao = {}", problemIoFileDao);
        log.info("problemIoFileDao.getZip = {}", problemIoFileDao.getIoZipFile());
        log.info("problemIoFileDao.getFilePath = {}", problemIoFileDao.getPreFilePath());

        if (problemIoFileDao == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (problemIoFileDao.getPreFilePath() != null && !problemIoFileDao.getPreFilePath().equals("")) {
            problemCreateService.deletePreFile(problemIoFileDao.getPreFilePath());
        }


        IoFilePathDto ioFilePathDto = problemCreateService.convertIoZip(groupId, problemIoFileDao.getIoZipFile(), String.valueOf(UUID.randomUUID()));

        if (ioFilePathDto == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(ioFilePathDto, HttpStatus.OK);
    }

    @PostMapping("/{groupId}/problems/upload/server")
    public ResponseEntity<ServerIoFilePathDto> problemDockerTest(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemServerDao problemServerDao) throws IOException {

        log.info("problemIoFileDao = {}", problemServerDao);
        log.info("problemIoFileDao.getZip = {}", problemServerDao.getIoZipFile());
        log.info("problemIoFileDao.getFilePath = {}", problemServerDao.getProblemFile());


        if (problemServerDao == null || problemServerDao.getProblemFile() == null || problemServerDao.getIoZipFile() == null) {
            log.info("not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("problemServerDao.data = {}", problemServerDao.getIoZipFile());

        ServerIoFilePathDto serverIoFilePathDto = problemCreateService.convertServerFile(groupId, problemServerDao, String.valueOf(UUID.randomUUID()));

        log.info("mainFilePath = {}", serverIoFilePathDto.getMainFilePath());

        ServerIoFilePathResDto serverIoFilePathDtoReq = httpConnectionService.requestProblemCreateTest(serverIoFilePathDto);

        return new ResponseEntity(serverIoFilePathDtoReq, HttpStatus.OK);
    }


//    @GetMapping
//    public ResponseEntity<SolveResponseDto> getSolveResponse(SolveRequestDto solveRequestDto) throws JsonProcessingException {
//
//        SolveResponseDto solveResponseDto = httpConnectionService.requestSolveScore(solveRequestDto);
//
//        log.info("solveId = {}", solveResponseDto.getSolveId());
//        log.info("score = {}", solveResponseDto.getScore());
//        log.info("testStatus = {}", solveResponseDto.getTestStatus());
//
//        return new ResponseEntity<>(solveResponseDto, HttpStatus.OK);
//    }

}

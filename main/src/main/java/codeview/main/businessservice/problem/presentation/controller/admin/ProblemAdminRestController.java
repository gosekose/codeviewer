package codeview.main.businessservice.problem.presentation.controller.admin;

import codeview.main.businessservice.membergroup.application.GroupService;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.application.ProblemCreateService;
import codeview.main.businessservice.problem.application.ProblemEditService;
import codeview.main.businessservice.problemrelation.application.ProblemScoreService;
import codeview.main.businessservice.problem.application.ProblemService;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problem.presentation.dao.ProblemIoFileDao;
import codeview.main.businessservice.problem.presentation.dao.ProblemServerDao;
import codeview.main.businessservice.problem.presentation.dto.IoFileDataDto;
import codeview.main.businessservice.problem.presentation.dto.ProblemCreatedResultDto;
import codeview.main.businessservice.problem.presentation.dto.ServerIoFilePathDto;
import codeview.main.businessservice.problem.presentation.error.ProblemError;
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
public class ProblemAdminRestController {

    private final ProblemService problemService;
    private final GroupService groupService;
    private final ProblemCreateService problemCreateService;
    private final HttpConnectionService httpConnectionService;
    private final ProblemScoreService problemScoreService;
    private final ProblemEditService problemEditService;
    private final ProblemError problemError;
    
    @Transactional
    @PostMapping("/{groupId}/problems/new")
    public ResponseEntity<ProblemCreatedResultDto> postCreateProblem(
            @PathVariable("groupId") Integer groupId,
            @Validated @ModelAttribute ProblemCreateDao problemCreateDao,
            BindingResult bindingResult) throws IOException, NoSuchAlgorithmException {

        ResponseEntity responseEntity = problemError.checkCreateError(problemCreateDao, bindingResult);
        if (responseEntity != null) {
            return responseEntity;
        }

        Problem problem = problemCreateService.saveProblemAndFiles(groupId, problemCreateDao);

        if (problem == null) {
            return new ResponseEntity<>(ProblemCreatedResultDto.builder().message("입출력 파일의 개수가 다릅니다.").build(), HttpStatus.BAD_REQUEST);
        }

        removeProblemTemporaryUUidFolder(problemCreateDao.getPreFilePath(), groupId);

        problemScoreService.saveProblemScore(problemCreateDao, problem); // score save;
        problemCreateService.saveDesAndIoExample(problemCreateDao, problem); // descriptions, ioExamples save
        Long problemId = problemService.save(problem);

        return new ResponseEntity<ProblemCreatedResultDto>(ProblemCreatedResultDto
                .builder()
                .problemId(problemId)
                .build(), HttpStatus.OK);
    }



    @Transactional
    @PostMapping("/{groupId}/problems/{problemId}/edit")
    public ResponseEntity<ProblemCreatedResultDto> postEditProblem(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            @Validated @ModelAttribute ProblemCreateDao problemCreateDao,
            BindingResult bindingResult) throws IOException, NoSuchAlgorithmException {

        ResponseEntity responseEntity = problemError.checkCommonError(problemCreateDao, bindingResult);
        if(responseEntity != null) {
            return responseEntity;
        }

        Problem problem = problemService.findById(Long.valueOf(problemId));
        Problem updateProblem = problemEditService.editProblem(groupId, problem, problemCreateDao);

        problemEditService.editProblemIoExample(updateProblem, problemCreateDao);
        problemEditService.editProblemDescription(updateProblem, problemCreateDao);
        problemEditService.editProblemScore(updateProblem, problemCreateDao);

        if (problemCreateDao.getPreFilePath() != null && !problemCreateDao.getPreFilePath().equals("")) {
            MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));
            problemCreateService.removeFolderNotUsedInGroup(memberGroup);
        }

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

        removeProblemTemporaryUUidFolder(problemIoFileDao.getPreFilePath(), groupId);

        IoFileDataDto ioFileDataDto = problemCreateService.convertIoZip(groupId, problemIoFileDao.getIoZipFile(), String.valueOf(UUID.randomUUID()));

        String[] split = ioFileDataDto.getFolderPath().split("/");
        String folderPath = split[split.length-2] + "/" + split[split.length-1];
        ioFileDataDto.setFolderPath(folderPath);


        if (ioFileDataDto == null) { return new ResponseEntity(HttpStatus.NOT_FOUND); }
        return new ResponseEntity(ioFileDataDto, HttpStatus.OK);
    }


    @PostMapping("/{groupId}/problems/{problemId}/upload/io")
    public ResponseEntity<IoFileDataDto> uploadEditIoFile(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            @ModelAttribute ProblemIoFileDao problemIoFileDao) throws IOException {

        if (problemIoFileDao == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (problemIoFileDao.getPreFilePath() != null && !problemIoFileDao.getPreFilePath().equals("")) {
            problemCreateService.removeFolder(problemIoFileDao.getPreFilePath());
        }

        Problem problem = problemService.findById(Long.valueOf(problemId));

        IoFileDataDto ioFileDataDto = problemEditService.convertIoZipRetainFolder(problemIoFileDao.getIoZipFile(), problem.getProblemInputIoFile().getInputStoreFolderPath());

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
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ServerIoFilePathDto serverIoFilePathDto = problemCreateService.convertServerFile(groupId, problemServerDao, String.valueOf(UUID.randomUUID()));
        ServerIoFileDemoTestResDto serverIoFilePathDtoReq = httpConnectionService.requestProblemCreateTest(serverIoFilePathDto);

        return new ResponseEntity(serverIoFilePathDtoReq, HttpStatus.OK);
    }


    private void removeProblemTemporaryUUidFolder(String problemIoFileDao, Integer groupId) {
        if (problemIoFileDao != null && !problemIoFileDao.equals("")) {
            if (problemIoFileDao.split("/")[0].equals(String.valueOf(groupId))) {
                problemCreateService.removeFolderPreFile(problemIoFileDao);
            }
        }
    }
}

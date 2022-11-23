package codeview.main.problem.presentation;

import codeview.main.problem.application.ProblemCreateService;
import codeview.main.problem.application.ProblemService;
import codeview.main.problem.domain.Problem;
import codeview.main.problem.presentation.dao.ProblemCreateDao;
import codeview.main.problem.presentation.dao.ProblemDockerDao;
import codeview.main.problem.presentation.dao.ProblemIoFileDao;
import codeview.main.problem.presentation.dto.DockerIoFilePathDto;
import codeview.main.problem.presentation.dto.IoFilePathDto;
import codeview.main.problem.presentation.dto.ProblemIdDto;
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

    @PostMapping("/{groupId}/problems/new")
    public ResponseEntity<ProblemIdDto> postCreateProblem(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemCreateDao problemCreateDao) throws IOException {

        Problem problem = problemCreateService.getProblem(groupId, problemCreateDao);
        Long problemId = problemService.save(problem);
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


        log.info("problemIoFileDao.getIoFile() = {}", problemIoFileDao.getIoZipFile());
        log.info("groupId = {}", groupId);

        if (problemIoFileDao == null || problemIoFileDao.getIoZipFile() == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        IoFilePathDto ioFilePathDto = problemCreateService.convertIoZip(groupId, problemIoFileDao.getIoZipFile(), String.valueOf(UUID.randomUUID()));
        return new ResponseEntity(ioFilePathDto, HttpStatus.OK);
    }

    @PostMapping("/{groupId}/problems/upload/docker")
    public ResponseEntity<DockerIoFilePathDto> problemDockerTest(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemDockerDao problemDockerDao) throws IOException {

        if (problemDockerDao == null || problemDockerDao.getMainSource() == null || problemDockerDao.getIoFileZip() == null) {
            log.info("not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        log.info("problemDockerDao.data = {}", problemDockerDao.getIoFileZip());

        DockerIoFilePathDto dockerIoFilePathDto = problemCreateService.convertDocker(groupId, problemDockerDao, String.valueOf(UUID.randomUUID()));

        log.info("dockerFile = {}", dockerIoFilePathDto.getDocker());

        return new ResponseEntity(dockerIoFilePathDto, HttpStatus.OK);
    }


}

package codeview.main.problem.presentation;

import codeview.main.membergroup.application.GroupService;
import codeview.main.problem.application.ProblemCreateService;
import codeview.main.problem.application.ProblemService;
import codeview.main.problem.domain.Problem;
import codeview.main.problem.infra.util.filestore.PythonFileStore;
import codeview.main.problem.presentation.dto.ProblemCreateVO;
import codeview.main.problem.presentation.dto.ProblemIdDto;
import codeview.main.problemdescription.application.ProblemDescriptionService;
import codeview.main.problemdescription.application.ProblemIoExampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/groups/admin")
@Slf4j
@RequiredArgsConstructor
public class ProblemAdminRestCreateController {

    private final PythonFileStore pythonFileStore;
    private final GroupService groupService;
    private final ProblemService problemService;

    private final ProblemDescriptionService problemDescriptionService;
    private final ProblemIoExampleService problemIoExampleService;

    private final ProblemCreateService problemCreateService;

    @PostMapping("/{groupId}/problems/new")
    public ResponseEntity<ProblemIdDto> postCreateProblem(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemCreateVO problemCreateVO) throws IOException {

        Problem problem = problemCreateService.getProblem(groupId, problemCreateVO);
        Long problemId = problemService.save(problem);
        problemCreateService.saveProblemData(problemCreateVO, problem);

        return new ResponseEntity<ProblemIdDto>(ProblemIdDto
                .builder()
                .problemId(problemId)
                .build(), HttpStatus.OK);
    }

}

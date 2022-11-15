package codeview.main.problem.presentation;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.membergroup.application.MemberGroupService;
import codeview.main.problem.application.ProblemService;
import codeview.main.problem.domain.Problem;
import codeview.main.problem.domain.UploadFile;
import codeview.main.problem.infra.util.filestore.PythonFileStore;
import codeview.main.problem.presentation.dto.ProblemCreateVO;
import codeview.main.problemdescription.application.ProblemDescriptionService;
import codeview.main.problemdescription.application.ProblemIoExampleService;
import codeview.main.problemdescription.domain.ProblemDescription;
import codeview.main.problemdescription.domain.ProblemIoExample;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static codeview.main.problem.infra.util.FileConverter.*;

@RestController
@RequestMapping("/api/v1/groups/admin")
@Slf4j
@RequiredArgsConstructor
public class ProblemRestCreateController {

    private final PythonFileStore pythonFileStore;
    private final MemberGroupService memberGroupService;
    private final ProblemService problemService;

    private final ProblemDescriptionService problemDescriptionService;
    private final ProblemIoExampleService problemIoExampleService;

    @PostMapping("/{groupId}/problems/new")
    public ResponseEntity<BaseEntity> postCreateProblem(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute ProblemCreateVO problemCreateVO) throws IOException {

        UploadFile problemFile = getUploadFile(problemCreateVO.getProblemFile(), groupId);
        UploadFile shellFile = getUploadFile(problemCreateVO.getShellFile(), groupId);
        UploadFile inputFile = getUploadFile(problemCreateVO.getInputFile(), groupId);

        Problem problem = getProblem(groupId, problemCreateVO, problemFile, shellFile, inputFile);

        problemService.save(problem);


        if (problemCreateVO.getDescriptions() != null) {
            for (int i=1; i<=problemCreateVO.getDescriptions().size(); i++) {
                saveProblemDescription(problemCreateVO, problem, i);}
        }

        if (problemCreateVO.getInputs() != null) {
            for (int i=1; i<=problemCreateVO.getInputs().size(); i++) {
                saveProblemIoExample(problemCreateVO, problem, i);}
        }

        return new ResponseEntity<BaseEntity>(HttpStatus.OK);
    }

    private void saveProblemIoExample(ProblemCreateVO problemCreateVO, Problem problem, int i) {
        problemIoExampleService.save(
                ProblemIoExample.builder()
                        .number(i)
                        .problem(problem)
                        .inputSource(problemCreateVO.getInputs().get(i))
                        .outputSource(problemCreateVO.getOutputs().get(i))
                        .build()
        );
    }


    private void saveProblemDescription(ProblemCreateVO problemCreateVO, Problem problem, int i) {
        problemDescriptionService.save(
                ProblemDescription.builder()
                .problem(problem)
                .number(i)
                .description(problemCreateVO.getDescriptions().get(i))
                .build());
    }


    private UploadFile getUploadFile(MultipartFile problemCreateVO, Integer groupId) throws IOException {
        return pythonFileStore.storeFile(problemCreateVO, String.valueOf(groupId));
    }

    private Problem getProblem(Integer groupId, ProblemCreateVO problemCreateVO, UploadFile problemFile, UploadFile shellFile, UploadFile inputFile) {
        Problem problem = Problem.builder()
                .name(problemCreateVO.getName())
                .memberGroup(memberGroupService.findById(Long.valueOf(groupId)))
                .openTime(problemCreateVO.getOpenTime())
                .closedTime(problemCreateVO.getClosedTime())
                .problemFile(toProblemFile(problemFile))
                .shellFile(toShellFile(shellFile))
                .inputFile(toInputFile(inputFile))
                .build();
        return problem;
    }
}

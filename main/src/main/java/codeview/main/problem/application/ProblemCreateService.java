package codeview.main.problem.application;

import codeview.main.membergroup.application.GroupService;
import codeview.main.problem.domain.Problem;
import codeview.main.problem.domain.UploadFile;
import codeview.main.problem.infra.util.filestore.PythonFileStore;
import codeview.main.problem.presentation.dto.ProblemCreateVO;
import codeview.main.problemdescription.domain.ProblemDescription;
import codeview.main.problemdescription.domain.ProblemIoExample;
import codeview.main.problemdescription.infra.repository.ProblemDescriptionRepository;
import codeview.main.problemdescription.infra.repository.ProblemIoExampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static codeview.main.problem.infra.util.FileConverter.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProblemCreateService {

    private final PythonFileStore pythonFileStore;
    private final GroupService groupService;

    private final ProblemIoExampleRepository problemIoExampleRepository;
    private final ProblemDescriptionRepository problemDescriptionRepository;

    @Transactional
    private void saveProblemIoExample(ProblemCreateVO problemCreateVO, Problem problem, int i) {
        problemIoExampleRepository.save(
                ProblemIoExample.builder()
                        .number(i+1)
                        .problem(problem)
                        .inputSource(problemCreateVO.getInputs().get(i))
                        .outputSource(problemCreateVO.getOutputs().get(i))
                        .build()
        );
    }

    @Transactional
    private void saveProblemDescription(ProblemCreateVO problemCreateVO, Problem problem, int i) {
        problemDescriptionRepository.save(
                ProblemDescription.builder()
                        .problem(problem)
                        .number(i+1)
                        .description(problemCreateVO.getDescriptions().get(i))
                        .build());
    }

    public Problem getProblem(Integer groupId, ProblemCreateVO problemCreateVO) throws IOException {

        UploadFile problemFile = getUploadFile(problemCreateVO.getProblemFile(), groupId);
        UploadFile shellFile = getUploadFile(problemCreateVO.getShellFile(), groupId);
        UploadFile inputFile = getUploadFile(problemCreateVO.getInputFile(), groupId);

        Problem problem = Problem.builder()
                .name(problemCreateVO.getName())
                .memberGroup(groupService.findById(Long.valueOf(groupId)))
                .openTime(problemCreateVO.getOpenTime())
                .closedTime(problemCreateVO.getClosedTime())
                .problemFile(toProblemFile(problemFile))
                .shellFile(toShellFile(shellFile))
                .inputFile(toInputFile(inputFile))
                .build();
        return problem;
    }

    public void saveProblemData(ProblemCreateVO problemCreateVO, Problem problem) {

        if (problemCreateVO.getDescriptions() != null) {
            for (int i=0; i<problemCreateVO.getDescriptions().size(); i++) {
                saveProblemDescription(problemCreateVO, problem, i);}
        }

        if (problemCreateVO.getInputs() != null) {
            for (int i=0; i<problemCreateVO.getInputs().size(); i++) {
                saveProblemIoExample(problemCreateVO, problem, i);}
        }
    }


    private UploadFile getUploadFile(MultipartFile problemCreateVO, Integer groupId) throws IOException {

        return pythonFileStore.storeFile(problemCreateVO, String.valueOf(groupId));
    }
}

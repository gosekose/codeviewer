package codeview.main.businessservice.problem.presentation.utils;

import codeview.main.businessservice.problem.application.ProblemService;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.infra.util.filestore.CommonFilStore;
import codeview.main.businessservice.problem.presentation.dto.ProblemAdminEditDto;
import codeview.main.businessservice.problemdescription.application.ProblemDescriptionService;
import codeview.main.businessservice.problemdescription.application.ProblemIoExampleService;
import codeview.main.businessservice.problemdescription.domain.ProblemDescription;
import codeview.main.businessservice.problemdescription.domain.ProblemIoExample;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Getter
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemPage {

    private final CommonFilStore commonFilStore;
    private final ProblemService problemService;
    private final ProblemDescriptionService problemDescriptionService;
    private final ProblemIoExampleService problemIoExampleService;

    public void getProblemPage(Model model, Integer groupId, Integer problemId) throws MalformedURLException {

        Problem problem = problemService.findById(Long.valueOf(problemId));
        List<ProblemDescription> descriptions = problemDescriptionService.findAllByProblem(problem);
        List<ProblemIoExample> ioExamples = problemIoExampleService.findAllByProblem(problem);

        String problemStoreName = problem.getProblemFile().getProblemStoreName();
        String problemUploadName = problem.getProblemFile().getProblemUploadName();

        UrlResource resource = new UrlResource("file:" + problemStoreName);

        ProblemAdminEditDto problemAdminEditDto = ProblemAdminEditDto.builder()
                .name(problem.getName())
                .problemType(problem.getProblemType())
                .openTime(problem.getOpenTime())
                .closedTime(problem.getClosedTime())
                .problemInputIoFile(problem.getProblemInputIoFile())
                .problemDifficulty(problem.getProblemDifficulty())
                .totalScore(problem.getTotalScore())
                .problemLanguage(problem.getProblemLanguage())
                .build();

        model.addAttribute("groupId", groupId);
        model.addAttribute("problemId", problemId);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("ioExamples", ioExamples);
        model.addAttribute("problemAdminEditDto", problemAdminEditDto);

    }


}

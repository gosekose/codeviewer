package codeview.main.problem.presentation.utils;

import codeview.main.problem.application.ProblemService;
import codeview.main.problem.domain.Problem;
import codeview.main.problemdescription.application.ProblemDescriptionService;
import codeview.main.problemdescription.application.ProblemIoExampleService;
import codeview.main.problemdescription.domain.ProblemDescription;
import codeview.main.problemdescription.domain.ProblemIoExample;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Getter
@Component
@RequiredArgsConstructor
public class ProblemPage {

    private final ProblemService problemService;
    private final ProblemDescriptionService problemDescriptionService;
    private final ProblemIoExampleService problemIoExampleService;

    public void getProblemPage(Model model, Integer groupId, Integer problemId) {

        Problem problem = problemService.findById(Long.valueOf(problemId));

        List<ProblemDescription> descriptions = problemDescriptionService.findAllByProblem(problem);
        List<ProblemIoExample> ioExamples = problemIoExampleService.findAllByProblem(problem);

        model.addAttribute("groupId", groupId);
        model.addAttribute("problemId", problemId);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("ioExamples", ioExamples);

    }
}

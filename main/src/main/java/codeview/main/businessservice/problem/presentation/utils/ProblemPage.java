package codeview.main.businessservice.problem.presentation.utils;

import codeview.main.businessservice.problem.application.ProblemService;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.presentation.dto.ProblemAdminEditDto;
import codeview.main.businessservice.problemdescription.application.ProblemDescriptionService;
import codeview.main.businessservice.problemdescription.application.ProblemIoExampleService;
import codeview.main.businessservice.problemdescription.domain.ProblemDescription;
import codeview.main.businessservice.problemdescription.domain.ProblemIoExample;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class ProblemPage {

    private final ProblemService problemService;
    private final ProblemDescriptionService problemDescriptionService;
    private final ProblemIoExampleService problemIoExampleService;

    @Cacheable(cacheNames = "problem", key = "#groupId + #problemId")
    public void getProblemPage(Model model, Integer groupId, Integer problemId) {

        Problem problem = problemService.findById(Long.valueOf(problemId));
        List<ProblemDescription> descriptions = problemDescriptionService.findAllByProblem(problem);
        List<ProblemIoExample> ioExamples = problemIoExampleService.findAllByProblem(problem);

        ProblemAdminEditDto problemAdminEditDto = ProblemAdminEditDto.builder()
                .name(problem.getName())
                .problemType(problem.getProblemType())
                .openTime(problem.getOpenTime())
                .closedTime(problem.getClosedTime())
                .build();

        model.addAttribute("groupId", groupId);
        model.addAttribute("problemId", problemId);
        model.addAttribute("descriptions", descriptions);
        model.addAttribute("ioExamples", ioExamples);
        model.addAttribute("problemAdminEditDto", problemAdminEditDto);

        log.info("descriptions = {}", descriptions.size());
        log.info("ioExamples = {}", ioExamples.size());
    }


}

package codeview.main.problem.presentation.controller.user;

import codeview.main.problem.application.ProblemService;
import codeview.main.problem.infra.repository.query.ProblemSearchPageCondition;
import codeview.main.problem.infra.repository.query.ProblemSearchPageDto;
import codeview.main.problem.presentation.utils.ProblemPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/v1/groups/{groupId}/problems")
@RequiredArgsConstructor
public class ProblemMemberUiController {

    private final ProblemPage problemPage;
    private final ProblemService problemService;


    @GetMapping
    public String getProblemList(
            @PathVariable("groupId") Integer groupId,
            @PageableDefault Pageable pageable,
            ProblemSearchPageCondition condition,
            Model model) {

        condition.setMemberGroupId(Long.valueOf(groupId));

        Page<ProblemSearchPageDto> problemSearchPage = problemService.getProblemSearchPage(condition, pageable);
        model.addAttribute("problems", problemSearchPage);
        model.addAttribute("groupId", groupId);

        return "problems/users/group-problems";

    }


    @GetMapping("/{problemId}")
    public String getProblemPage(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            Model model) {

        problemPage.getProblemPage(model,groupId, problemId);

        log.info("problemAdmin groupId = {}, problemId = {}", groupId, problemId);

        return "problems/users/member-problem";
    }

}

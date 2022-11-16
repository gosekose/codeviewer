package codeview.main.problem.presentation;

import codeview.main.problem.presentation.utils.ProblemPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/v1/groups/{groupId}/problems/{problemId}")
@RequiredArgsConstructor
public class ProblemMemberUiController {

    private final ProblemPage problemPage;

    @GetMapping
    public String getProblemPage(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            Model model) {

        problemPage.getProblemPage(model,groupId, problemId);

        log.info("problemAdmin groupId = {}, problemId = {}", groupId, problemId);

        return "problems/members/member-problem";
    }

}

package codeview.main.problem.presentation;

import codeview.main.common.application.CsrfProviderService;
import codeview.main.problem.presentation.utils.ProblemPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/api/v1/groups/admin/{groupId}/problems")
@RequiredArgsConstructor
public class ProblemAdminUiController {

    private final ProblemPage problemPage;
    private final CsrfProviderService csrfProviderService;

    @GetMapping("/new")
    public String getCreateProblem(
            @PathVariable("groupId") String groupId,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("_csrf", csrfProviderService.createCsrf(request));
        model.addAttribute("groupId", groupId);

        return "problems/admins/create-my-problem";
    }

    @GetMapping("/{problemId}")
    public String getProblemIdAdminPage(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            Model model) {

        problemPage.getProblemPage(model, groupId, problemId);

        log.info("problemAdmin groupId = {}, problemId = {}", groupId, problemId);

        return "problems/admins/edit-my-problem";
    }

    @GetMapping
    public String getProblemsAdminPage(@PathVariable("groupId") Integer groupId, Model model) {


        return "problems/admins/";
    }


}

package codeview.main.problem.presentation;

import codeview.main.common.application.CsrfProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/v1/groups/admin")
@Slf4j
@RequiredArgsConstructor
public class ProblemCreateController {

    private final CsrfProviderService csrfProviderService;

    @GetMapping("/{groupId}/problems/new")
    public String getCreateProblem(
            @PathVariable("groupId") String groupId,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("_csrf", csrfProviderService.createCsrf(request));
        model.addAttribute("groupId", groupId);

        return "problems/create-my-problem";
    }


}

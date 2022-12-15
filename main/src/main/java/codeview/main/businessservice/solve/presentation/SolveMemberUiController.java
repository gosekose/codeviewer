package codeview.main.businessservice.solve.presentation;

import codeview.main.businessservice.problem.presentation.dto.ProblemSolveForm;
import codeview.main.businessservice.problem.presentation.utils.ProblemPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/groups/{groupId}/problems")
public class SolveMemberUiController {

    private final ProblemPage problemPage;

    @GetMapping("/{problemId}")
    public String getProblemSolvePage(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            Model model) throws MalformedURLException {

        problemPage.getProblemUserDto(model, groupId, problemId);

        log.info("problemAdmin groupId = {}, problemId = {}", groupId, problemId);

        return "problems/users/solve-my-problem";
    }

    @PostMapping("/{problemId}")
    public String postProblemSolve(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("problemId") Integer problemId,
            @Validated @ModelAttribute ProblemSolveForm problemSolveForm, BindingResult bindingResult){

        log.info("problemSolveForm.getProblemLanguage() = {}", problemSolveForm.getProblemLanguage());
        log.info("problemSolveForm.getMyPythonEditor() = {}", problemSolveForm.getMyPythonEditor());
        log.info("problemSolveForm.getMyJavaEditor() = {}", problemSolveForm.getMyJavaEditor());

        String redirect = "redirect:/" + "api/v1/groups/" +groupId + "/problems/" + problemId;

        return redirect;
    }

}

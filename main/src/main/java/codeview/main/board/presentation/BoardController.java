package codeview.main.board.presentation;

import codeview.main.board.presentation.dao.BoardForm;
import codeview.main.problem.application.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/{groupId}/problems/board")
public class BoardController {

    private final ProblemService problemService;

    @GetMapping
    public String getBoardForm(
            @PathVariable("groupId") Integer groupId,
            Model model) {

        model.addAttribute("groupId", groupId);
        model.addAttribute("boardForm", new BoardForm());

        return "boards/user/create-board";
    }

    @PostMapping
    public String postBoardForm(
            @PathVariable("groupId") Integer groupId,
            @ModelAttribute BoardForm boardForm,
            RedirectAttributes redirectAttributes) {

        return "boards/user/create-board";
    }

}

package codeview.main.board.presentation;

import codeview.main.board.presentation.dao.BoardForm;
import codeview.main.problem.application.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/{groupId}/problems/board")
public class BoardController {

    private final ProblemService problemService;

    @GetMapping
    public String getBoardPage(
            @PathVariable("groupId") Integer groupId) {

        return "";
    }

    @GetMapping
    public String getBoardForm(
            @PathVariable("groupId") Integer groupId,
            Model model) {

        model.addAttribute("groupId", groupId);
        model.addAttribute("boardForm", new BoardForm());

        return "";
    }

}

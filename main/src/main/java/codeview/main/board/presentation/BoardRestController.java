package codeview.main.board.presentation;

import codeview.main.problem.application.ProblemService;
import codeview.main.problem.infra.repository.query.ProblemSearchForBoardDto;
import codeview.main.problem.infra.repository.query.ProblemSearchPageCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/{groupId}/problems/board/problemList")
public class BoardRestController {

    private final ProblemService problemService;

    @GetMapping
    public Page<ProblemSearchForBoardDto> getProblemForBoard(
            @PathVariable("groupId") Integer groupId,
            ProblemSearchPageCondition condition,
            @PageableDefault Pageable pageable) {

        condition.setMemberGroupId(Long.valueOf(groupId));

        return problemService.getProblemForBoard(condition, pageable);
    }
}

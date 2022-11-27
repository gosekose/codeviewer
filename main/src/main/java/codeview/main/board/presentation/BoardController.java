package codeview.main.board.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.board.application.BoardMultipartFileService;
import codeview.main.board.application.BoardService;
import codeview.main.board.domain.Board;
import codeview.main.board.presentation.dao.BoardForm;
import codeview.main.common.presentation.page.PageUtils;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.application.ProblemService;
import codeview.main.problem.infra.repository.query.ProblemSearchForBoardDto;
import codeview.main.problem.infra.repository.query.ProblemSearchPageCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/{groupId}/problems/board")
public class BoardController {

    private final ProblemService problemService;
    private final GroupService groupService;

    private final BoardService boardService;
    private final BoardMultipartFileService boardMultipartFileService;
    private final MemberService memberService;

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
            @PathVariable("groupId") Long groupId,
            @AuthenticationPrincipal PrincipalUser principalUser,
            @Validated @ModelAttribute BoardForm boardForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "boards/user/create-board";
        }

        log.info("boardTitle = {}", boardForm.getBoardName());
        log.info("boardMain = {}", boardForm.getBoardMain());

        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());
        MemberGroup memberGroup = groupService.findById(groupId);

        Board board = boardService.saveBoard(memberGroup, boardForm, member);
        boardMultipartFileService.saveBoardMultipartFile(groupId, boardForm, board);

        return "redirect:";
    }

    @GetMapping("/problemList")
    public String getProblemForBoard(
            @PathVariable("groupId") Integer groupId,
            ProblemSearchPageCondition condition,
            @PageableDefault Pageable pageable,
            Model model) {

        condition.setMemberGroupId(Long.valueOf(groupId));

        Page<ProblemSearchForBoardDto> problemForBoard = problemService.getProblemForBoard(condition, pageable);
        PageUtils.modelPagingAndModel(problemForBoard, model, "problemForBoard");

        model.addAttribute("groupId", groupId);

        return "boards/user/problem-popup";
    }

}

package codeview.main.board.application;

import codeview.main.board.domain.Board;
import codeview.main.board.infra.repository.BoardRepository;
import codeview.main.board.presentation.dao.BoardForm;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.application.ProblemService;
import codeview.main.problem.domain.Problem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final ProblemService problemService;
    private final BoardRepository boardRepository;
    private final GroupService groupService;

    public Board saveBoard(MemberGroup memberGroup, BoardForm boardForm, Member writer) throws IOException {

        Problem problem = null;

        if (boardForm.getProblemId() != null) {
            problem = problemService.findById(Long.valueOf(boardForm.getProblemId()));
        }

        Board board = boardRepository.save(
                Board.builder()
                        .boardName(boardForm.getBoardName())
                        .boardMain(boardForm.getBoardMain())
                        .anonymousCheck(boardForm.getAnonymousCheck())
                        .nondisclosure(boardForm.getNondisclosure())
                        .writer(writer)
                        .problem(problem)
                        .memberGroup(memberGroup)
                        .build()
        );

        return board;
    }

}

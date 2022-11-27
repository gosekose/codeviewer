package codeview.main.board.application;

import codeview.main.board.domain.Board;
import codeview.main.board.infra.repository.BoardQueryDslRepositoryImpl;
import codeview.main.board.infra.repository.BoardRepository;
import codeview.main.board.infra.repository.query.BoardListCondition;
import codeview.main.board.infra.repository.query.BoardListDto;
import codeview.main.board.presentation.dao.BoardForm;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.application.ProblemService;
import codeview.main.problem.domain.Problem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static codeview.main.board.domain.enumtype.AnonymousCheck.ON;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final ProblemService problemService;
    private final BoardRepository boardRepository;
    private final BoardQueryDslRepositoryImpl boardQueryDslRepository;

    @Transactional
    public Board saveBoard(MemberGroup memberGroup, BoardForm boardForm, Member writer) throws IOException {

        Problem problem = null;

        if (boardForm.getProblemId() != null) {
            problem = problemService.findById(Long.valueOf(boardForm.getProblemId()));
        }

        String viewName;

        if (boardForm.getAnonymousCheck().equals(ON)) {
            viewName = "***";
        } else {
            viewName = writer.getMemberName();
        }

        Board board = boardRepository.save(
                Board.builder()
                        .boardName(boardForm.getBoardName())
                        .boardMain(boardForm.getBoardMain())
                        .anonymousCheck(boardForm.getAnonymousCheck())
                        .nondisclosure(boardForm.getNondisclosure())
                        .writer(writer)
                        .viewName(viewName)
                        .problem(problem)
                        .memberGroup(memberGroup)
                        .build()
        );

        return board;
    }


    public Page<BoardListDto> getBoardListDtoPage(BoardListCondition condition, Pageable pageable) {
        return boardQueryDslRepository.searchBoardListPage(condition, pageable);
    }


}

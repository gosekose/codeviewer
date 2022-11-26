package codeview.main.board.infra.repository;

import codeview.main.board.infra.repository.query.BoardListCondition;
import codeview.main.board.infra.repository.query.BoardListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardQueryDslRepository {

    Page<BoardListDto> searchBoardListPage(BoardListCondition condition, Pageable pageable);

}

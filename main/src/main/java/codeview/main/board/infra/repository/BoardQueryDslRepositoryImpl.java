package codeview.main.board.infra.repository;

import codeview.main.board.infra.repository.query.BoardListCondition;
import codeview.main.board.infra.repository.query.BoardListDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardQueryDslRepositoryImpl implements BoardQueryDslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<BoardListDto> searchBoardListPage(BoardListCondition condition, Pageable pageable) {
//
//        List<BoardListDto> content = query
//                .select(
//                        new QBoardListDto(
//                                board.id,
//                                board.boardName,
//                                board.boardMain,
//                                member.memberName,
//                                board.anonymousCheck,
//                                board.nondisclosure,
//                                board.createdAt,
//                                board.modifiedAt,
//                                board.problem.id,
//                                board.problem.name
//                        )
//                )
//                .from(board)
//                .innerJoin()
//                .on()
//                .where()

        return null;
    }
}

package codeview.main.board.infra.repository;

import codeview.main.board.domain.enumtype.Nondisclosure;
import codeview.main.board.infra.repository.query.BoardListCondition;
import codeview.main.board.infra.repository.query.BoardListDto;
import codeview.main.board.infra.repository.query.QBoardListDto;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.domain.Problem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.board.domain.QBoard.board;
import static codeview.main.problem.domain.QProblem.problem;

@Repository
@RequiredArgsConstructor
public class BoardQueryDslRepositoryImpl implements BoardQueryDslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<BoardListDto> searchBoardListPage(BoardListCondition condition, Pageable pageable) {

        List<BoardListDto> content = query
                .select(
                        new QBoardListDto(
                                board.id,
                                board.boardName,
                                board.viewName,
                                board.createdAt,
                                board.modifiedAt,
                                board.problem.id,
                                board.problem.name
                        )
                )
                .from(board)
                .innerJoin(board.problem, problem)
                .where(
                        memberGroupEq(condition.getMemberGroup()),
                        problemEq(condition.getProblem()),
                        memberEq(condition.getMember()),
                        nondisclosureEq(condition.getNondisclosure())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(board.count())
                .from(board)
                .innerJoin(board.problem, problem)
                .where(
                        memberGroupEq(condition.getMemberGroup()),
                        problemEq(condition.getProblem()),
                        memberEq(condition.getMember()),
                        nondisclosureEq(condition.getNondisclosure())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }

    private BooleanExpression nondisclosureEq(Nondisclosure nondisclosure) {
        return nondisclosure != null ? board.nondisclosure.eq(nondisclosure) : null;
    }

    private BooleanExpression memberEq(Member member) {
        return member != null ? board.writer.eq(member) : null;
    }

    private BooleanExpression problemEq(Problem problem) {
        return problem != null ? board.problem.eq(problem) : null;
    }

    private BooleanExpression memberGroupEq(MemberGroup memberGroup) {

        return memberGroup != null ? board.memberGroup.eq(memberGroup) : null;

    }
}

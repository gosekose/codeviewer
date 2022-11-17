package codeview.main.Solve.infra.repository;

import codeview.main.Solve.domain.Solve;
import codeview.main.Solve.infra.repository.query.QSolvesOfProblemPageDto;
import codeview.main.Solve.infra.repository.query.SolvesOfProblemCondition;
import codeview.main.Solve.infra.repository.query.SolvesOfProblemDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.Solve.domain.QSolve.solve;
import static codeview.main.member.domain.QMember.member;
import static codeview.main.problem.domain.QProblem.problem;

@Repository
@RequiredArgsConstructor
public class SolveQueryDslRepositoryImpl implements SolveQueryDslRepository{

    private final JPAQueryFactory query;

    @Override
    public List<Solve> searchAllSolve(SolvesOfProblemCondition condition) {
        return query
                .selectFrom(solve)
                .where(
                        problemIdEq(condition.getProblemId()),
                        memberIdEq(condition.getMemberId()),
                        scoreGoe(condition.getGoeScore()),
                        scoreLoe(condition.getLoeScore())
                )
                .fetch();
    }

    @Override
    public List<SolvesOfProblemDto> searchSolvesOfProblemDto(SolvesOfProblemCondition condition) {
        return query
                .select(
                        new QSolvesOfProblemPageDto(
                                solve.id,
                                problem.id,
                                problem.name,
                                member.id,
                                member.memberName,
                                solve.createdAt,
                                solve.score))
                .from(solve)
                .join(solve.problem, problem)
                .join(solve.member, member)
                .where(
                        problemIdEq(condition.getProblemId()),
                        memberIdEq(condition.getMemberId()),
                        scoreGoe(condition.getLoeScore()),
                        scoreLoe(condition.getGoeScore()))
                .fetch();
    }

    @Override
    public Page<SolvesOfProblemDto> searchPageComplex(SolvesOfProblemCondition condition, Pageable pageable) {

        List<SolvesOfProblemDto> content = query
                .select(
                        new QSolvesOfProblemPageDto(
                                solve.id,
                                problem.id,
                                problem.name,
                                member.id,
                                member.memberName,
                                solve.createdAt,
                                solve.score))
                .from(solve)
                .join(solve.problem, problem)
                .join(solve.member, member)
                .where(
                        problemIdEq(condition.getProblemId()),
                        memberIdEq(condition.getMemberId()),
                        scoreGoe(condition.getLoeScore()),
                        scoreLoe(condition.getGoeScore())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(solve.count())
                .from(solve)
                .where(
                        problemIdEq(condition.getProblemId()),
                        memberIdEq(condition.getMemberId()),
                        scoreGoe(condition.getLoeScore()),
                        scoreLoe(condition.getGoeScore())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    private BooleanExpression scoreLoe(Integer score) {
        return score != null ? solve.score.loe(score) : null;
    }

    private BooleanExpression scoreGoe(Integer score) {
        return score != null ? solve.score.goe(score) : null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? solve.member.id.eq(memberId) : null;
    }

    private BooleanExpression problemIdEq(Long problemId) {
        return problemId != null ? solve.problem.id.eq(problemId) : null;
    }

}

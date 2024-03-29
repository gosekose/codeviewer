package codeview.main.businessservice.solve.infra.repository;

import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.infra.repository.query.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.businessservice.member.domain.QMember.member;
import static codeview.main.businessservice.problem.domain.QProblem.problem;
import static codeview.main.businessservice.solve.domain.QSolve.solve;


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
                        new QSolvesOfProblemDto(
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
                        new QSolvesOfProblemDto(
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

    @Override
    public List<MemberSolveInfoDto> searchMemberSolvesNoProblemId(MemberSolveInfoCondition condition) {

        return query
                .select(
                    new QMemberSolveInfoDto(
                            solve.problem.id,
                            solve.id.count(),
                            solve.problem.name,
                            solve.score.max(),
                            solve.createdAt.max()))
                .from(solve)
                .innerJoin(solve.problem, problem)
                .on(problem.memberGroup.id.eq(condition.getGroupId()))
                .where(
                        memberIdEq(condition.getMemberId())
                )
                .groupBy(problem.id)
                .fetch();
    }

    @Override
    public List<MemberSolveInfoDto> searchMemberSolvesByProblemId(MemberSolveInfoCondition condition) {
        return query
                .select(
                        new QMemberSolveInfoDto(
                                solve.id,
                                solve.problem.id,
                                solve.problem.name,
                                solve.score,
                                solve.createdAt))
                .from(solve)
                .join(solve.problem, problem).on(
                        problem.memberGroup.id.eq(condition.getGroupId()))
                .where(
                        problemIdEq(condition.getProblemId()),
                        memberIdEq(condition.getMemberId())
                )
                .orderBy(solve.problem.id.asc(), solve.createdAt.asc())
                .fetch();
    }

    @Override
    public List<SolvesOfProblemChartMyScoreDto> searchMemberSolvesMyChartDto(MemberSolveInfoCondition condition) {

        return query
                .select(
                        new QSolvesOfProblemChartMyScoreDto(
                                solve.id,
                                solve.number,
                                solve.score))
                .from(solve)
                .innerJoin(solve.problem, problem)
                .on(solve.problem.id.eq(condition.getProblemId()))
                .where(
                        memberIdEq(condition.getMemberId())
                )
                .orderBy(solve.number.asc())
                .fetch();
    }

    @Override
    public List<SolvesOfProblemChartOtherScoreDto> searchMemberSolvesOtherChartDto(MemberSolveInfoCondition condition) {
        return query
                .select(
                        new QSolvesOfProblemChartOtherScoreDto(
                                solve.number,
                                solve.score.avg()
                        ))
                .from(solve)
                .innerJoin(solve.problem, problem)
                .on(solve.problem.id.eq(condition.getProblemId()))
                .groupBy(solve.number)
                .orderBy(solve.number.asc())
                .limit(condition.getSolveCount())
                .fetch();

    }

    private BooleanExpression groupIdEq(Long groupId) {
        return groupId != null ? solve.problem.memberGroup.id.eq(groupId) : null;
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

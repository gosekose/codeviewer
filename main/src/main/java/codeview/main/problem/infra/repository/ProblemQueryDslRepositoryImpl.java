package codeview.main.problem.infra.repository;

import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.domain.Problem;
import codeview.main.problem.infra.repository.query.ProblemListPageDto;
import codeview.main.problem.infra.repository.query.ProblemListSearchCondition;
import codeview.main.problem.infra.repository.query.QProblemListPageDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.problem.domain.QProblem.problem;

@Repository
@RequiredArgsConstructor
public class ProblemQueryDslRepositoryImpl implements ProblemQueryDslRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Problem> search(ProblemListSearchCondition condition) {
        return query.selectFrom(problem)
                .where(
                        memberGroupEq(condition.getMemberGroup()),
                        nameContains(condition.getName())
                )
                .fetch();
    }

    @Override
    public Page<ProblemListPageDto> searchPageComplex(ProblemListSearchCondition condition, Pageable pageable) {

        List<ProblemListPageDto> content = query
                .select(
                        new QProblemListPageDto(
                                problem.id,
                                problem.name,
                                problem.problemFile.problemUploadName,
                                problem.shellFile.shellUploadName,
                                problem.inputFile.inputUploadName,
                                problem.createdAt,
                                problem.modifiedAt))
                .from(problem)
                .where(
                        memberGroupEq(condition.getMemberGroup()),
                        nameContains(condition.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(problem.count())
                .from(problem)
                .where(
                        memberGroupEq(condition.getMemberGroup()),
                        nameContains(condition.getName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    private BooleanExpression nameContains(String name) {
        return name != null? problem.name.contains(name) : null;
    }

    private BooleanExpression memberGroupEq(MemberGroup memberGroup) {
        return memberGroup != null ? problem.memberGroup.eq(memberGroup) : null;
    }


}

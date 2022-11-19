package codeview.main.problem.infra.repository;

import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.infra.repository.query.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.groupstorage.domain.QGroupStorage.groupStorage;
import static codeview.main.member.domain.QMember.member;
import static codeview.main.membergroup.domain.QMemberGroup.memberGroup;
import static codeview.main.problem.domain.QProblem.problem;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProblemQueryDslRepositoryImpl implements ProblemQueryDslRepository {

    private final JPAQueryFactory query;

    @Override
    public List<ProblemSearchPageDto> searchProblem(ProblemSearchPageCond condition) {

        return query
                .select(
                        new QProblemSearchPageDto(
                                problem.id,
                                problem.name,
                                memberGroup.name,
                                memberGroup.creator.memberName))
                .distinct()
                .from(problem)
                .join(problem.memberGroup, memberGroup)
                .join(memberGroup.groupStorage, groupStorage)
                .join(groupStorage.member, member)
                .where(
                        memberGroupIdEq(condition.getMemberGroupId()),
                        problemNameContains(condition.getProblemName()),
                        memberCreatorContains(condition.getCreatorName()),
                        groupNameContains(condition.getGroupName()),
                        memberIdEq(condition.getMemberId())
                )
                .fetch();
    }


    @Override
    public Page<ProblemSearchPageDto> searchProblemPageComplex(ProblemSearchPageCond condition, Pageable pageable) {

        List<ProblemSearchPageDto> content = query
                .select(
                        new QProblemSearchPageDto(
                                problem.id,
                                problem.name,
                                memberGroup.name,
                                memberGroup.creator.memberName))
                .distinct()
                .from(problem)
                .join(problem.memberGroup, memberGroup)
                .join(memberGroup.groupStorage, groupStorage)
                .join(groupStorage.member, member)
                .where(
                        memberGroupIdEq(condition.getMemberGroupId()),
                        problemNameContains(condition.getProblemName()),
                        memberCreatorContains(condition.getCreatorName()),
                        groupNameContains(condition.getGroupName()),
                        memberIdEq(condition.getMemberId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(problem.count())
                .from(problem)
                .where(
                        memberGroupIdEq(condition.getMemberGroupId()),
                        problemNameContains(condition.getProblemName()),
                        memberCreatorContains(condition.getCreatorName()),
                        groupNameContains(condition.getGroupName()),
                        memberIdEq(condition.getMemberId())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<ProblemDetailPageDto> searchDetailPageComplex(ProblemDetailPageCond condition, Pageable pageable) {

        List<ProblemDetailPageDto> content = query
                .select(
                        new QProblemDetailPageDto(
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


    private BooleanExpression memberGroupIdEq(Long groupId) {
        return groupId != null ? problem.memberGroup.id.eq(groupId) : null;
    }

    private BooleanExpression memberCreatorContains(String creatorName) {
        return creatorName != null ? problem.memberGroup.creator.memberName.contains(creatorName) : null;
    }

    private BooleanExpression groupNameContains(String groupName) {
        return groupName != null ? problem.memberGroup.name.contains(groupName) : null;
    }

    private BooleanExpression problemNameContains(String problemName) {
        return problemName != null ? problem.name.contains(problemName) : null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? groupStorage.member.id.eq(memberId) : null;
    }
}

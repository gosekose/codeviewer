package codeview.main.businessservice.membergroup.infra.repository.membergroup;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.QGroupJoinRequest;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupJoinStatus;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.query.MemberGroupSearchCondition;
import codeview.main.businessservice.membergroup.presentation.dto.GroupForPageDto;
import codeview.main.businessservice.membergroup.presentation.dto.QGroupForPageDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.businessservice.membergroup.domain.QMemberGroup.memberGroup;


@Repository
@RequiredArgsConstructor
public class MemberGroupQueryDslRepositoryImpl implements MemberGroupQueryDslRepository {

    private final JPAQueryFactory query;


    @Override
    public List<MemberGroup> search(MemberGroupSearchCondition condition) {
        return query.selectFrom(memberGroup)
                .where(
                        adminEq(condition.getAdmin()),
                        visibilityEq(condition.getVisibility()),
                        nameContains(condition.getName())
                )
                .fetch();
    }

    @Override
    public Page<GroupForPageDto> searchPageComplex(MemberGroupSearchCondition condition, Pageable pageable) {

        List<GroupForPageDto> content = query
                .select(
                        new QGroupForPageDto(
                                memberGroup.id,
                                memberGroup.name,
                                memberGroup.memberGroupVisibility,
                                memberGroup.maxMember,
                                memberGroup.joinClosedTime,
                                memberGroup.groupAutoJoin))
                .from(memberGroup)
                .where(
                        memberNe(condition.getCreator()),
                        adminEq(condition.getAdmin()),
                        visibilityEq(condition.getVisibility()),
                        nameContains(condition.getName()),
                        groupJoinEq(condition.getGroupAutoJoin())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(memberGroup.count())
                .from(memberGroup)
                .where(
                        memberNe(condition.getCreator()),
                        adminEq(condition.getAdmin()),
                        visibilityEq(condition.getVisibility()),
                        nameContains(condition.getName()),
                        groupJoinEq(condition.getGroupAutoJoin())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GroupForPageDto> searchGroupByJoinStatus(MemberGroupSearchCondition condition, Pageable pageable) {

        QGroupJoinRequest groupJoinRequest = new QGroupJoinRequest("groupJoinRequest");


        List<GroupForPageDto> content = query
                .select(
                        new QGroupForPageDto(
                                memberGroup.id,
                                memberGroup.name,
                                memberGroup.memberGroupVisibility,
                                memberGroup.maxMember,
                                memberGroup.joinClosedTime,
                                memberGroup.groupAutoJoin))
                .from(memberGroup)
                .where(
                        memberNe(condition.getCreator()),
                        adminEq(condition.getAdmin()),
                        visibilityEq(condition.getVisibility()),
                        nameContains(condition.getName()),
                        groupJoinEq(condition.getGroupAutoJoin()),
                        memberGroup.id.notIn(
                                JPAExpressions
                                        .select(groupJoinRequest.memberGroup.id)
                                        .from(groupJoinRequest)
                                        .where(
                                                groupJoinRequest.member.eq(condition.getCreator()),
                                                groupJoinRequest.groupJoinStatus.ne(GroupJoinStatus.ONEDELETE)
                                        )
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(memberGroup.count())
                .from(memberGroup)
                .where(
                        memberNe(condition.getCreator()),
                        adminEq(condition.getAdmin()),
                        visibilityEq(condition.getVisibility()),
                        nameContains(condition.getName()),
                        groupJoinEq(condition.getGroupAutoJoin()),
                        memberGroup.id.notIn(
                                JPAExpressions
                                        .select(groupJoinRequest.memberGroup.id)
                                        .from(groupJoinRequest)
                                        .where(
                                                groupJoinRequest.member.eq(condition.getCreator()),
                                                groupJoinRequest.groupJoinStatus.ne(GroupJoinStatus.ONEDELETE)
                                        )
                        )
                );


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression adminEq(Member admin) {
        return admin != null ? memberGroup.creator.eq(admin) : null;
    }
    private BooleanExpression memberNe(Member member) {
        return member != null ? memberGroup.creator.ne(member) : null;
    }


    private BooleanExpression nameContains(String name) {
        return name != null ? memberGroup.name.contains(name) : null;
    }

    private BooleanExpression visibilityEq(MemberGroupVisibility memberGroupVisibility) {
        return memberGroupVisibility != null ? memberGroup.memberGroupVisibility.eq(memberGroupVisibility) : null;
    }

    private BooleanExpression groupJoinEq(GroupAutoJoin groupAutoJoin) {
        return groupAutoJoin != null ? memberGroup.groupAutoJoin.eq(groupAutoJoin) : null;
    }

}

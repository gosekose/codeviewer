package codeview.main.businessservice.member.infra.repository;

import codeview.main.businessservice.member.infra.repository.query.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static codeview.main.businessservice.groupstorage.domain.QGroupStorage.groupStorage;
import static codeview.main.businessservice.member.domain.QMember.member;
import static codeview.main.businessservice.school.domain.QSchool.school;

@Repository
@RequiredArgsConstructor
public class MemberQueryDslRepositoryImpl implements MemberQueryDslRepository {

    private final JPAQueryFactory query;

    @Override
    public GroupMemberInfo searchMemberInfoUsingGroup(GroupMemberInfoCondition condition) {
        return query
                .select(
                        new QGroupMemberInfo(
                                member.memberName,
                                member.picture,
                                school.schoolName,
                                member.department,
                                member.privateIdInSchool,
                                groupStorage.memberGroupAuthority,
                                groupStorage.createdAt))
                .distinct()
                .from(member)
                .leftJoin(member.school, school)
                .join(groupStorage).on(groupStorage.member.id.eq(member.id), groupIdEq(condition.getGroupId()))
                .where(
                        memberIdEq(condition.getMemberId())
                )
                .fetchOne();

    }

    @Override
    public MemberProfileDto getMemberProfile(MemberCondition condition) {

        return query
                .select(
                        new QMemberProfileDto(
                                member.userComment,
                                member.memberName,
                                school.schoolName,
                                member.department,
                                member.privateIdInSchool,
                                member.work,
                                member.registrationId,
                                member.email,
                                member.facebookEmail,
                                member.linkedInEmail,
                                member.githubEmail
                        )
                )
                .from(member)
                .leftJoin(member.school, school)
                .where(
                        memberIdEq(condition.getMemberId())
                )
                .fetchOne();
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? member.id.eq(memberId) : null;
    }

    private  BooleanExpression groupIdEq(Long groupId) {
        return groupId != null ? groupStorage.memberGroup.id.eq(groupId) : null;
    }





}

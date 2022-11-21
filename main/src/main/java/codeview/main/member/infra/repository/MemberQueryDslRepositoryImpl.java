package codeview.main.member.infra.repository;

import codeview.main.member.infra.repository.query.GroupMemberInfo;
import codeview.main.member.infra.repository.query.GroupMemberInfoCondition;
import codeview.main.member.infra.repository.query.QGroupMemberInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static codeview.main.groupstorage.domain.QGroupStorage.groupStorage;
import static codeview.main.member.domain.QMember.member;
import static codeview.main.school.domain.QSchool.school;

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
                                school.name,
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

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? member.id.eq(memberId) : null;
    }

    private  BooleanExpression groupIdEq(Long groupId) {
        return groupId != null ? groupStorage.memberGroup.id.eq(groupId) : null;
    }
}

package codeview.main.groupstorage.infra.repository;

import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.groupstorage.infra.repository.query.list.GroupStorageListCondition;
import codeview.main.groupstorage.infra.repository.query.list.GroupStorageListDto;
import codeview.main.groupstorage.infra.repository.query.list.QGroupStorageListDto;
import codeview.main.groupstorage.infra.repository.query.member.MembersOfGroupCondition;
import codeview.main.groupstorage.infra.repository.query.member.MembersOfGroupPageDto;
import codeview.main.groupstorage.infra.repository.query.member.QMembersOfGroupPageDto;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.MemberGroupAuthority;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.groupstorage.domain.QGroupStorage.groupStorage;
import static codeview.main.member.domain.QMember.member;
import static codeview.main.membergroup.domain.QMemberGroup.memberGroup;
import static codeview.main.school.domain.QSchool.school;

@Repository
@RequiredArgsConstructor
public class GroupStorageQueryDslRepositoryImpl implements GroupStorageQueryDslRepository{

    private final JPAQueryFactory query;

    @Override
    public List<GroupStorage> search(MembersOfGroupCondition condition) {
        return query.selectFrom(groupStorage)
                .where(
                        memberEq(condition.getMember()),
                        memberGroupEq(condition.getMemberGroup()),
                        memberAuthEq(condition.getMemberGroupAuthority())
                )
                .fetch();
    }

    @Override
    public Page<MembersOfGroupPageDto> searchMemberOfGroupComplex(MembersOfGroupCondition condition, Pageable pageable) {

        List<MembersOfGroupPageDto> content = query
                .select(
                        new QMembersOfGroupPageDto(
                                member.id,
                                member.memberName,
                                member.department,
                                member.privateIdInSchool,
                                groupStorage.memberGroupAuthority,
                                groupStorage.createdAt))
                .from(groupStorage)
                .leftJoin(groupStorage.member, member)
                .where(
                        memberGroupEq(condition.getMemberGroup()),
                        memberEq(condition.getMember()),
                        memberAuthEq(condition.getMemberGroupAuthority()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(groupStorage.count())
                .from(groupStorage)
                .where(
                        memberGroupEq(condition.getMemberGroup()),
                        memberEq(condition.getMember()),
                        memberAuthEq(condition.getMemberGroupAuthority()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<GroupStorageListDto> searchGroupListComplex(GroupStorageListCondition condition, Pageable pageable) {
        List<GroupStorageListDto> content =  query
                .select(
                        new QGroupStorageListDto(
                                memberGroup.id,
                                memberGroup.name,
                                memberGroup.creator.memberName,
                                memberGroup.creator.school.name,
                                memberGroup.creator.department))
                .from(groupStorage)
                .innerJoin(groupStorage.memberGroup, memberGroup)
                .innerJoin(groupStorage.member, member)
                .leftJoin(memberGroup.creator.school, school)
                .where(
                        memberEq(condition.getMember())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(groupStorage.count())
                .from(groupStorage)
                .innerJoin(groupStorage.memberGroup, memberGroup)
                .innerJoin(groupStorage.member, member)
                .leftJoin(memberGroup.creator.school, school)
                .where(
                        memberEq(condition.getMember())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression memberAuthEq(MemberGroupAuthority memberGroupAuthority) {
        return memberGroupAuthority != null ? groupStorage.memberGroupAuthority.eq(memberGroupAuthority) : null;
    }

    private BooleanExpression memberGroupEq(MemberGroup memberGroup) {
        return memberGroup != null ? groupStorage.memberGroup.eq(memberGroup) : null;
    }

    private BooleanExpression memberEq(Member member) {
        return member != null ? groupStorage.member.eq(member) : null;
    }
}

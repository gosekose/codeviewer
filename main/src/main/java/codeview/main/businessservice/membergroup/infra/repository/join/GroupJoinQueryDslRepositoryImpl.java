package codeview.main.businessservice.membergroup.infra.repository.join;

import codeview.main.businessservice.membergroup.infra.repository.join.query.JoinRequestCondition;
import codeview.main.businessservice.membergroup.infra.repository.join.query.JoinRequestQueryPageDto;
import codeview.main.businessservice.membergroup.infra.repository.join.query.QJoinRequestQueryPageDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.businessservice.member.domain.QMember.member;
import static codeview.main.businessservice.membergroup.domain.QGroupJoinRequest.groupJoinRequest;
import static codeview.main.businessservice.membergroup.domain.QMemberGroup.memberGroup;
import static codeview.main.businessservice.school.domain.QSchool.school;


@Repository
@Slf4j
@RequiredArgsConstructor
public class GroupJoinQueryDslRepositoryImpl implements GroupJoinQueryDslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<JoinRequestQueryPageDto> findJoinRequestQueryPageDto(JoinRequestCondition condition, Pageable pageable) throws Exception{

        List<JoinRequestQueryPageDto> content = query.
                select(
                        new QJoinRequestQueryPageDto(
                                groupJoinRequest.memberGroup.id,
                                groupJoinRequest.memberGroup.name,
                                groupJoinRequest.member.id,
                                groupJoinRequest.member.memberName,
                                groupJoinRequest.member.school.schoolName,
                                groupJoinRequest.member.department,
                                groupJoinRequest.member.privateIdInSchool))
                .from(groupJoinRequest)
                .join(groupJoinRequest.memberGroup, memberGroup)
                .on(memberGroup.creator.eq(condition.getMember()))
                .join(groupJoinRequest.member, member)
                .leftJoin(groupJoinRequest.member.school, school)
                .where(
                        groupJoinRequest.groupJoinStatus.eq(condition.getGroupJoinStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(groupJoinRequest.count())
                .from(groupJoinRequest)
                .innerJoin(groupJoinRequest.memberGroup, memberGroup)
                .on(memberGroup.creator.id.eq(condition.getMember().getId()))
                .innerJoin(groupJoinRequest.member, member)
                .leftJoin(groupJoinRequest.member.school, school)
                .where(
                        groupJoinRequest.groupJoinStatus.eq(condition.getGroupJoinStatus())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}


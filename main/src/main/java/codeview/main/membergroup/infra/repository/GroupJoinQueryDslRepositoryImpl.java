package codeview.main.membergroup.infra.repository;

import codeview.main.membergroup.infra.repository.query.dto.JoinRequestCondition;
import codeview.main.membergroup.infra.repository.query.dto.JoinRequestQueryPageDto;
import codeview.main.membergroup.infra.repository.query.dto.QJoinRequestQueryPageDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.member.domain.QMember.member;
import static codeview.main.membergroup.domain.QGroupJoinRequest.groupJoinRequest;
import static codeview.main.membergroup.domain.QMemberGroup.memberGroup;
import static codeview.main.school.domain.QSchool.school;

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
                                groupJoinRequest.member.school.name,
                                groupJoinRequest.member.department,
                                groupJoinRequest.member.privateIdInSchool))
                .from(groupJoinRequest)
                .innerJoin(groupJoinRequest.memberGroup, memberGroup)
                .on(memberGroup.creator.id.eq(condition.getMember().getId()))
                .innerJoin(groupJoinRequest.member, member)
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


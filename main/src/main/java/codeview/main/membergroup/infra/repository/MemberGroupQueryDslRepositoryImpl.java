package codeview.main.membergroup.infra.repository;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.MemberGroupVisibility;
import codeview.main.membergroup.infra.dao.MemberGroupSearchCondition;
import codeview.main.membergroup.presentation.dto.MemberGroupDto;
import codeview.main.membergroup.presentation.dto.QMemberGroupDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static codeview.main.membergroup.domain.QMemberGroup.memberGroup;

@Repository
@RequiredArgsConstructor
public class MemberGroupQueryDslRepositoryImpl implements MemberGroupQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<MemberGroup> search(MemberGroupSearchCondition condition) {
        return jpaQueryFactory.selectFrom(memberGroup)
                .where(
                        memberEq(condition.getMember()),
                        visibilityEq(condition.getVisibility()),
                        nameContains(condition.getName())
                )
                .fetch();
    }

    @Override
    public Page<MemberGroupDto> searchPageComplex(MemberGroupSearchCondition condition, Pageable pageable) {

        List<MemberGroupDto> content = jpaQueryFactory
                .select(new QMemberGroupDto(memberGroup.name, memberGroup.memberGroupVisibility, memberGroup.maxMember, memberGroup.joinClosedTime))
                .from(memberGroup)
                .where(
                        memberEq(condition.getMember()),
                        visibilityEq(condition.getVisibility()),
                        nameContains(condition.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(memberGroup.count())
                .from(memberGroup)
                .where(
                        memberEq(condition.getMember()),
                        visibilityEq(condition.getVisibility()),
                        nameContains(condition.getName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression memberEq(Member member) {
        return member != null ? memberGroup.creator.eq(member) : null;
    }

    private BooleanExpression nameContains(String name) {
        return name != null ? memberGroup.name.contains(name) : null;
    }

    private BooleanExpression visibilityEq(MemberGroupVisibility memberGroupVisibility) {
        return memberGroupVisibility != null ? memberGroup.memberGroupVisibility.eq(memberGroupVisibility) : null;
    }


}

package codeview.main.businessservice.school.infra.repository;

import codeview.main.businessservice.school.domain.School;
import codeview.main.businessservice.school.infra.dao.SchoolSearchCondition;
import codeview.main.school.presentation.dto.QSchoolDto;
import codeview.main.businessservice.school.presentation.dto.SchoolDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static codeview.main.school.domain.QSchool.school;


@Repository
@Slf4j
@RequiredArgsConstructor
public class SchoolQueryDslRepositoryImpl implements SchoolQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<School> search(SchoolSearchCondition condition) {

        return jpaQueryFactory.selectFrom(school)
                .where(nameContains(condition.getName()),
                        addressContains(condition.getAddress()))
                .fetch();
    }

    @Override
    public Page<SchoolDto> searchPageComplex(SchoolSearchCondition condition, Pageable pageable) {

        List<SchoolDto> content = jpaQueryFactory
                .select(new QSchoolDto(
                        school.name,
                        school.address
                ))
                .from(school)
                .where(nameContains(condition.getName()),
                        addressContains(condition.getAddress()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(school.count())
                .from(school)
                .where(nameContains(condition.getName()),
                        addressContains(condition.getAddress()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }

    private BooleanExpression addressContains(String address) {

        return StringUtils.isEmpty(address) ? null : school.address.contains(address);
    }

    private BooleanExpression nameContains(String name) {

        return StringUtils.isEmpty(name) ? null : school.name.contains(name);
    }

}

package codeview.main.businessservice.school.infra.repository;

import codeview.main.businessservice.school.domain.School;
import codeview.main.businessservice.school.infra.dao.SchoolSearchCondition;
import codeview.main.businessservice.school.presentation.dto.SchoolDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SchoolQueryDslRepository {
    List<School> search(SchoolSearchCondition schoolSearchCondition);

    Page<SchoolDto> searchPageComplex(SchoolSearchCondition condition, Pageable pageable);

}

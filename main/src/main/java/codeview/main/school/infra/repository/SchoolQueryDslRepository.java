package codeview.main.school.infra.repository;

import codeview.main.school.infra.dao.SchoolSearchCondition;
import codeview.main.school.presentation.dto.SchoolDto;
import codeview.main.school.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SchoolQueryDslRepository {
    List<School> search(SchoolSearchCondition schoolSearchCondition);

    Page<SchoolDto> searchPageComplex(SchoolSearchCondition condition, Pageable pageable);

}

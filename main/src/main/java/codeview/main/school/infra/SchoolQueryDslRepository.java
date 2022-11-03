package codeview.main.school.infra;

import codeview.main.school.application.dto.SchoolDto;
import codeview.main.school.domain.School;
import codeview.main.school.domain.SchoolSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolQueryDslRepository {
    List<School> search(SchoolSearchCondition schoolSearchCondition);

    Page<SchoolDto> searchPageComplex(SchoolSearchCondition condition, Pageable pageable);

}

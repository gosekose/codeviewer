package codeview.main.problem.infra.repository;

import codeview.main.problem.domain.Problem;
import codeview.main.problem.infra.repository.query.ProblemListPageDto;
import codeview.main.problem.infra.repository.query.ProblemListSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemQueryDslRepository {

    List<Problem> search(ProblemListSearchCondition problemListSearchCondition);

    Page<ProblemListPageDto> searchPageComplex(ProblemListSearchCondition condition, Pageable pageable);

}

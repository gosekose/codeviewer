package codeview.main.problem.infra.repository;

import codeview.main.problem.infra.repository.query.ProblemDetailPageCondition;
import codeview.main.problem.infra.repository.query.ProblemDetailPageDto;
import codeview.main.problem.infra.repository.query.ProblemSearchPageCondition;
import codeview.main.problem.infra.repository.query.ProblemSearchPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemQueryDslRepository {

    List<ProblemSearchPageDto> searchProblem(ProblemSearchPageCondition condition);

    Page<ProblemSearchPageDto> searchProblemPageComplex(ProblemSearchPageCondition condition, Pageable pageable);

    Page<ProblemDetailPageDto> searchDetailPageComplex(ProblemDetailPageCondition condition, Pageable pageable);

}

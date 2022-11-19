package codeview.main.problem.infra.repository;

import codeview.main.problem.infra.repository.query.ProblemDetailPageCond;
import codeview.main.problem.infra.repository.query.ProblemDetailPageDto;
import codeview.main.problem.infra.repository.query.ProblemSearchPageCond;
import codeview.main.problem.infra.repository.query.ProblemSearchPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemQueryDslRepository {

    List<ProblemSearchPageDto> searchProblem(ProblemSearchPageCond condition);

    Page<ProblemSearchPageDto> searchProblemPageComplex(ProblemSearchPageCond condition, Pageable pageable);

    Page<ProblemDetailPageDto> searchDetailPageComplex(ProblemDetailPageCond condition, Pageable pageable);

}

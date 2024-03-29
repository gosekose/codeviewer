package codeview.main.businessservice.problem.infra.repository;

import codeview.main.businessservice.problem.infra.repository.query.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemQueryDslRepository {

    List<ProblemSearchPageDto> searchProblem(ProblemSearchPageCondition condition);

    Page<ProblemSearchPageDto> searchProblemPageComplex(ProblemSearchPageCondition condition, Pageable pageable);

    Page<ProblemSearchForBoardDto> searchProblemForBoard(ProblemSearchPageCondition condition, Pageable pageable);

    Page<ProblemDetailPageDto> searchDetailPageComplex(ProblemDetailPageCondition condition, Pageable pageable);

    List<VisibleRecentProblemDto> searchVisibleRecentProblem(VisibleRecentProblemCondition condition);
    List<VisibleRecentProblemNoLoginDto> searchVisibleRecentProblemNoLogin(VisibleRecentProblemCondition condition);

}

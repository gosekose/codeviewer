package codeview.main.Solve.infra.repository;

import codeview.main.Solve.domain.Solve;
import codeview.main.Solve.infra.repository.query.SolvesOfProblemCondition;
import codeview.main.Solve.infra.repository.query.SolvesOfProblemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolveQueryDslRepository {

    List<Solve> searchAllSolve(SolvesOfProblemCondition condition);

    List<SolvesOfProblemDto> searchSolvesOfProblemDto(SolvesOfProblemCondition condition);

    Page<SolvesOfProblemDto> searchPageComplex(SolvesOfProblemCondition condition, Pageable pageable);
}

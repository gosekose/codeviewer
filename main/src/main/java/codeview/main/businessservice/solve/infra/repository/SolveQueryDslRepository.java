package codeview.main.businessservice.solve.infra.repository;

import codeview.main.businessservice.solve.infra.repository.query.*;
import codeview.main.businessservice.solve.domain.Solve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolveQueryDslRepository {

    List<Solve> searchAllSolve(SolvesOfProblemCondition condition);

    List<SolvesOfProblemDto> searchSolvesOfProblemDto(SolvesOfProblemCondition condition);

    Page<SolvesOfProblemDto> searchPageComplex(SolvesOfProblemCondition condition, Pageable pageable);

    List<MemberSolveInfoDto> searchMemberSolvesNoProblemId(MemberSolveInfoCondition condition);
    List<MemberSolveInfoDto> searchMemberSolvesByProblemId(MemberSolveInfoCondition condition);

    List<SolvesOfProblemChartMyScoreDto> searchMemberSolvesMyChartDto(MemberSolveInfoCondition condition);

    List<SolvesOfProblemChartOtherScoreDto> searchMemberSolvesOtherChartDto(MemberSolveInfoCondition condition);
}

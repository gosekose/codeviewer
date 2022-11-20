package codeview.main.solve.infra.repository;

import codeview.main.solve.domain.Solve;
import codeview.main.solve.infra.repository.query.MemberSolveInfoCondition;
import codeview.main.solve.infra.repository.query.MemberSolveInfoDto;
import codeview.main.solve.infra.repository.query.SolvesOfProblemCondition;
import codeview.main.solve.infra.repository.query.SolvesOfProblemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolveQueryDslRepository {

    List<Solve> searchAllSolve(SolvesOfProblemCondition condition);

    List<SolvesOfProblemDto> searchSolvesOfProblemDto(SolvesOfProblemCondition condition);

    Page<SolvesOfProblemDto> searchPageComplex(SolvesOfProblemCondition condition, Pageable pageable);

    /**
     *
     * cross join
     * ==> cross join시 데이터가 많으면 overflow 가능성
     * client에서 problem을 클릭하면 세부 내용을 확인할 수 있도록 api 변경
     *
     */
    List<MemberSolveInfoDto> searchMemberSolvesCrossJoin(MemberSolveInfoCondition condition);
    List<MemberSolveInfoDto> searchMemberSolves(MemberSolveInfoCondition condition);
}

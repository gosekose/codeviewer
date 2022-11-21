package codeview.main.solve.infra.repository;

import codeview.main.member.domain.Member;
import codeview.main.problem.domain.Problem;
import codeview.main.solve.domain.Solve;
import codeview.main.solve.infra.repository.query.SolvesOfProblemChartMyScoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolveRepository extends JpaRepository<Solve, Long> {

    @Query("select count(s) from Solve s where s.problem = :problem and s.member = :member")
    Integer getCountSolve(
            @Param("problem") Problem problem,
            @Param("member") Member member);

    @Query("select s from Solve s inner join s.problem")
    List<SolvesOfProblemChartMyScoreDto> getSolveChart();
}

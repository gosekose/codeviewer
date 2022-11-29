package codeview.main.businessservice.solve.infra.repository;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.infra.repository.query.SolvesOfProblemChartMyScoreDto;
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

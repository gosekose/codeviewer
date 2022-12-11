package codeview.main.businessservice.problem.infra.repository;

import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.domain.ProblemScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemScoreRepository extends JpaRepository<ProblemScore,Long> {

    @Query("select s from ProblemScore s where s.problem = :problem")
    List<ProblemScore> findProblemScoreByProblem(@Param("problem")Problem problem);
}

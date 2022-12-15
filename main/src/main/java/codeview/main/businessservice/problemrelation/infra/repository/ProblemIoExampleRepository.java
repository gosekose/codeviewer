package codeview.main.businessservice.problemrelation.infra.repository;

import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problemrelation.domain.ProblemIoExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemIoExampleRepository extends JpaRepository<ProblemIoExample, Long> {

    @Query("select p from ProblemIoExample p where p.problem = :problem order by p.number")
    List<ProblemIoExample> findAllByProblem(@Param("problem")Problem problem);


}

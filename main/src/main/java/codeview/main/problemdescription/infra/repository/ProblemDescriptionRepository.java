package codeview.main.problemdescription.infra.repository;

import codeview.main.problem.domain.Problem;
import codeview.main.problemdescription.domain.ProblemDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemDescriptionRepository extends JpaRepository<ProblemDescription, Long> {

    @Query("select p from ProblemDescription p where p.problem = :problem order by p.number")
    List<ProblemDescription> findAllByProblem(@Param("problem") Problem problem);

}

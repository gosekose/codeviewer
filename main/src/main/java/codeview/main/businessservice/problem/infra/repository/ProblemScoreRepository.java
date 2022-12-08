package codeview.main.businessservice.problem.infra.repository;

import codeview.main.businessservice.problem.domain.ProblemScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemScoreRepository extends JpaRepository<ProblemScore,Long> {
}

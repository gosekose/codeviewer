package codeview.main.problemdescription.infra.repository;

import codeview.main.problemdescription.domain.ProblemDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemDescriptionRepository extends JpaRepository<ProblemDescription, Long> {

}

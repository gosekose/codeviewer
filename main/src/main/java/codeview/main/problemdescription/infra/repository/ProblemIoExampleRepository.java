package codeview.main.problemdescription.infra.repository;

import codeview.main.problemdescription.domain.ProblemIoExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemIoExampleRepository extends JpaRepository<ProblemIoExample, Long> {
}

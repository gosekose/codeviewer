package codeview.main.problem.infra.repository;

import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("select p.id, p.name from Problem p where p.memberGroup = :memberGroup")
    List<Problem> findByMemberGroup(@Param("memberGroup")MemberGroup memberGroup);

}

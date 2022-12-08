package codeview.main.businessservice.solve.infra.repository;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.solve.domain.LastSolveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LastSolveStatusRepository extends JpaRepository<LastSolveStatus, Long> {

    @Query("select t from LastSolveStatus t where t.member = :member and t.problem = :problem")
    LastSolveStatus findByMemberAndProblem(@Param("member") Member member,
                                           @Param("problem") Problem problem);

}

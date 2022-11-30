package codeview.main.businessservice.problem.infra.repository;

import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("select p.id, p.name from Problem p where p.memberGroup = :memberGroup")
    List<Problem> findByMemberGroup(@Param("memberGroup")MemberGroup memberGroup);

    @Query("select p.problemInputIoFile from Problem p where p.memberGroup = :memberGroup")
    List<ProblemInputIoFile> findByMemberGroupForInputFolderPath(@Param("memberGroup")MemberGroup memberGroup);

}

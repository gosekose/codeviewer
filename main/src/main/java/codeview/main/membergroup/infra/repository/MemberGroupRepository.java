package codeview.main.membergroup.infra.repository;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long> {

    @Override List<MemberGroup> findAll();

    @Query("select m from MemberGroup m where m.creator = :member")
    List<MemberGroup> findAllByMember(@Param("member") Member member);

}

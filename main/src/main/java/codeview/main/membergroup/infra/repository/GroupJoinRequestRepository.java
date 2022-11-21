package codeview.main.membergroup.infra.repository;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.GroupJoinRequest;
import codeview.main.membergroup.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJoinRequestRepository extends JpaRepository<GroupJoinRequest, Long> {

    @Query("select g from GroupJoinRequest g where g.member = :member and g.memberGroup = :memberGroup")
    GroupJoinRequest findByMemberAndMemberGroup(
            @Param("member") Member member,
            @Param("memberGroup") MemberGroup memberGroup);

}

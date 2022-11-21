package codeview.main.groupstorage.infra.repository;

import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupStorageRepository extends JpaRepository<GroupStorage, Long> {

    @Query("select g from GroupStorage g where g.member = :member and g.memberGroup = :memberGroup")
    GroupStorage findByMemberAndMemberGroup(
            @Param("member") Member member,
            @Param("memberGroup") MemberGroup memberGroup);

    @Query("select g from GroupStorage g where g.memberGroup = :memberGroup")
    List<GroupStorage> findAllByMemberGroup(
            @Param("memberGroup") MemberGroup memberGroup);
}

package codeview.main.member.infra.repository;

import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.groupstorage.infra.repository.GroupStorageRepository;
import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.member.infra.repository.query.GroupMemberInfo;
import codeview.main.member.infra.repository.query.GroupMemberInfoCondition;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.MemberGroupAuthority;
import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.membergroup.infra.repository.membergroup.MemberGroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@Transactional
class MemberQueryDslRepositoryImplTest {

    @Autowired private MemberQueryDslRepositoryImpl memberQueryDslRepository;
    @Autowired private MemberRepository memberRepository;

    @Autowired private MemberGroupRepository memberGroupRepository;

    @Autowired private GroupStorageRepository groupStorageRepository;

    static Long memberId;
    static Long groupId;
    static Long groupStorageId;

    @Test
    public void 그룹_회원_정보검색() throws Exception {
        //given
        GroupMemberInfoCondition condition = new GroupMemberInfoCondition();
        condition.setMemberId(memberId);
        condition.setGroupId(groupId);

        //when

        GroupMemberInfo groupMemberInfos = memberQueryDslRepository.searchMemberInfoUsingGroup(condition);

        //then
        Assertions.assertThat(groupMemberInfos.getMemberName()).isEqualTo("koseyun");
        Assertions.assertThat(groupMemberInfos.getMemberGroupAuthority()).isEqualTo(MemberGroupAuthority.USER);
        Assertions.assertThat(groupMemberInfos.getDepartment()).isEqualTo("1111");
        Assertions.assertThat(groupMemberInfos.getPrivateIdInSchool()).isEqualTo("1111");

    }

    @BeforeEach
    public void doinit() {
        Member creator = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();


        Member member = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();

        member.updateProfile("koseyun",null,null,null,null,"1111","1111");

        memberRepository.save(creator);
        memberRepository.save(member);
        memberId = member.getId();

        MemberGroup group1 = MemberGroup.builder().creator(creator).maxMember(20)
                .description(String.valueOf(10)).joinClosedTime(LocalDateTime.now())
                .name("TEST1").memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();

        memberGroupRepository.save(group1);
        groupId = group1.getId();

        MemberGroup group2 = MemberGroup.builder().creator(creator).maxMember(20)
                .description(String.valueOf(11)).joinClosedTime(LocalDateTime.now())
                .name("TEST1").memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();

        memberGroupRepository.save(group2);

        GroupStorage groupStorage = GroupStorage.builder().member(member).memberGroup(group1).memberGroupAuthority(MemberGroupAuthority.USER).build();

        groupStorageRepository.save(groupStorage);


        groupStorageId = groupStorage.getId();
    }

}
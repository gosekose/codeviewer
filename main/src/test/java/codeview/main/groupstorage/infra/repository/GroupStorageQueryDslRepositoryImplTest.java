package codeview.main.groupstorage.infra.repository;

import codeview.main.businessservice.groupstorage.application.GroupStorageService;
import codeview.main.businessservice.groupstorage.infra.repository.query.list.GroupStorageListCondition;
import codeview.main.businessservice.groupstorage.infra.repository.query.list.GroupStorageListDto;
import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.MemberRepository;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.MemberGroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
class GroupStorageQueryDslRepositoryImplTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired GroupStorageService groupStorageService;
    @Autowired MemberGroupRepository memberGroupRepository;
    static Member creator;
    static Member member;
    static MemberGroup group1;
    static MemberGroup group2;
    static Long groupStorageId;

    @Test
    public void member로_가입한_그룹리스트_확인하기() throws Exception {
        //given
        GroupStorageListCondition condition = new GroupStorageListCondition();
        condition.setMember(member);
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<GroupStorageListDto> groupStorageListForMember = groupStorageService.getGroupStorageListForMember(condition, pageable);

        //then
        Assertions.assertThat(groupStorageListForMember.getContent().size()).isEqualTo(1);
        Assertions.assertThat(groupStorageListForMember.getContent().get(0).getGroupId()).isEqualTo(group1.getId());

    }

    /**
     *
     * creator: 그룹 개설자
     * member: 멤버 그룹 가입자
     *
     * group1: 그룹이 개설한 그룹
     * group2: 그룹이 개설한 그룹
     *
     * groupStorage1: 멤버가 가입한 그룹의 스토리지 (member, group1)
     * groupStorage2: 멤버가 가입하지 않은 그룹의 스토리지 (member, group2)
     *
     * test: groupStorageService
     * @param: groupStorageListCondition, Pageable
     * @Return: Page<GroupStorageListPageDto>
     */
    @BeforeEach
    public void init() {

        creator = createMembers(UUID.randomUUID().toString());
        memberRepository.save(creator);

        member = createMembers(UUID.randomUUID().toString());
        memberRepository.save(member);

        group1 = MemberGroup.builder()
                .creator(creator)
                .name("group1")
                .groupAutoJoin(GroupAutoJoin.ON)
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();

        group2 = MemberGroup.builder()
                .creator(creator)
                .name("group2")
                .groupAutoJoin(GroupAutoJoin.ON)
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();

        memberGroupRepository.save(group1);
        memberGroupRepository.save(group2);
        groupStorageId = groupStorageService.save(member, group1);

    }

    protected Member createMembers(String regisId) {

        return Member.builder()
                .registerId(regisId).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();

    }
}
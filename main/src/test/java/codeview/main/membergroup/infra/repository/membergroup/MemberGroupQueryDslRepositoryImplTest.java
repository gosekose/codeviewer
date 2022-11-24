package codeview.main.membergroup.infra.repository.membergroup;

import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.membergroup.infra.repository.membergroup.query.MemberGroupSearchCondition;
import codeview.main.membergroup.presentation.dto.GroupForPageDto;
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
class MemberGroupQueryDslRepositoryImplTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired GroupStorageService groupStorageService;
    @Autowired MemberGroupRepository memberGroupRepository;

    @Autowired MemberGroupQueryDslRepositoryImpl memberGroupQueryDslRepository;

    static Member creator;
    static Member member;
    static MemberGroup group1;
    static MemberGroup group2;
    static Long groupStorageId;


    @Test
    public void 내가_개설한_그룹_제외_검색() throws Exception {
        //given
        MemberGroupSearchCondition condition1 = new MemberGroupSearchCondition();
        condition1.setCreator(creator);

        MemberGroupSearchCondition condition2 = new MemberGroupSearchCondition();
        condition2.setCreator(member);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<GroupForPageDto> groupForPageDtos1 = memberGroupQueryDslRepository.searchPageComplex(condition1, pageable);
        Page<GroupForPageDto> groupForPageDtos2 = memberGroupQueryDslRepository.searchPageComplex(condition2, pageable);

        //then
        Assertions.assertThat(groupForPageDtos1.getContent().size()).isEqualTo(0);
        Assertions.assertThat(groupForPageDtos2.getContent().size()).isEqualTo(2);
    }

    @Test
    public void admin이_본인이_개설한_그룹_검색() throws Exception {
        //given
        MemberGroupSearchCondition condition1 = new MemberGroupSearchCondition();
        condition1.setAdmin(creator);

        MemberGroupSearchCondition condition2 = new MemberGroupSearchCondition();
        condition2.setAdmin(member);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<GroupForPageDto> groupForPageDtos1 = memberGroupQueryDslRepository.searchPageComplex(condition1, pageable);
        Page<GroupForPageDto> groupForPageDtos2 = memberGroupQueryDslRepository.searchPageComplex(condition2, pageable);

        //then
        Assertions.assertThat(groupForPageDtos1.getContent().size()).isEqualTo(2);
        Assertions.assertThat(groupForPageDtos2.getContent().size()).isEqualTo(0);

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
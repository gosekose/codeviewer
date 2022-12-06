package codeview.main.membergroup.infra.repository;

import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.MemberRepository;
import codeview.main.businessservice.membergroup.application.GroupJoinService;
import codeview.main.businessservice.membergroup.domain.GroupJoinRequest;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupJoinStatus;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.join.GroupJoinQueryDslRepositoryImpl;
import codeview.main.businessservice.membergroup.infra.repository.join.GroupJoinRequestRepository;
import codeview.main.businessservice.membergroup.infra.repository.join.query.JoinRequestCondition;
import codeview.main.businessservice.membergroup.infra.repository.join.query.JoinRequestQueryPageDto;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.MemberGroupRepository;
import codeview.main.businessservice.school.domain.School;
import codeview.main.businessservice.school.infra.repository.SchoolRepository;
import groovy.util.logging.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@Slf4j
@Transactional
class GroupJoinQueryDslRepositoryImplTest {

    @Autowired private MemberService memberService;
    @Autowired private GroupJoinQueryDslRepositoryImpl groupJoinQueryDslRepository;
    @Autowired private MemberGroupRepository memberGroupRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private GroupJoinRequestRepository groupJoinRequestRepository;
    @Autowired private SchoolRepository schoolRepository;

    @Autowired private GroupJoinService groupJoinService;


    static Member creator;
    static Member requestUser1;
    static Member requestUser2;
    static School school;
    static MemberGroup group1;
    static MemberGroup group2;
    static GroupJoinRequest groupJoinRequest1;
    static GroupJoinRequest groupJoinRequest2;
    static GroupJoinRequest groupJoinRequest3;
    static GroupJoinRequest groupJoinRequest4;

    static int member1Cnt;
    static int member2Cnt;

    @Test
    public void 그룹_요청_페이징_사이즈() throws Exception {
        //given
        JoinRequestCondition condition = new JoinRequestCondition();
        condition.setMember(creator);
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<JoinRequestQueryPageDto> joinRequestQueryPageDto = groupJoinQueryDslRepository.findJoinRequestQueryPageDto(condition, pageable);

        for (JoinRequestQueryPageDto requestQueryPageDto : joinRequestQueryPageDto.getContent()) {
            if (requestQueryPageDto.getUserId().equals(requestUser1.getId())) {
                member1Cnt ++;
            } else if (requestQueryPageDto.getUserId().equals(requestUser2.getId())) {
                member2Cnt ++;
            }
        }

        //then
        Assertions.assertThat(joinRequestQueryPageDto.getTotalElements()).isEqualTo(4);
        Assertions.assertThat(member1Cnt).isEqualTo(2);
        Assertions.assertThat(member2Cnt).isEqualTo(2);

    }


    /**
     *
     * creator : 그룹 생성자 (주어지는 memberId)
     * requestMember1, requestMember2 : 그룹 가입 요청을 한 사용자 2명
     * group1, group2: 유저가 가입하려고 하는 그룹 2개
     * school: 그룹 가입 요청한 사용자 학교
     * groupJoinRequest 1 ~ 4 : 사용자가 요청한 request
     *
     */
    @BeforeEach
    public void doInit() {

        creator = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        memberRepository.save(creator);

        school = School.builder()
                .schoolName("SeoulTech")
                .build();
        schoolRepository.save(school);


        requestUser1 = Member.builder()
                .registerId(String.valueOf(20)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        requestUser1.updateSchool(school);
        memberRepository.save(requestUser1);

        requestUser2 = Member.builder()
                .registerId(String.valueOf(20)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        requestUser2.updateSchool(school);
        memberRepository.save(requestUser2);

        group1 = MemberGroup.builder().creator(creator).maxMember(20)
                .description(String.valueOf(1)).joinClosedTime(LocalDateTime.now())
                .name("TEST1").memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();
        memberGroupRepository.save(group1);

        group2 = MemberGroup.builder().creator(creator).maxMember(20)
                .description(String.valueOf(1)).joinClosedTime(LocalDateTime.now())
                .name("TEST1").memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();
        memberGroupRepository.save(group2);


        groupJoinRequest1 = GroupJoinRequest.builder()
                .groupJoinStatus(GroupJoinStatus.WAIT)
                .memberGroup(group1)
                .denialCount(0)
                .member(requestUser1)
                .build();
        groupJoinService.save(groupJoinRequest1);

        groupJoinRequest2 = GroupJoinRequest.builder()
                .groupJoinStatus(GroupJoinStatus.WAIT)
                .memberGroup(group1)
                .denialCount(0)
                .member(requestUser2)
                .build();
        groupJoinService.save(groupJoinRequest2);

        groupJoinRequest3 = GroupJoinRequest.builder()
                .groupJoinStatus(GroupJoinStatus.WAIT)
                .memberGroup(group2)
                .denialCount(0)
                .member(requestUser1)
                .build();
        groupJoinService.save(groupJoinRequest3);

        groupJoinRequest4 = GroupJoinRequest.builder()
                .groupJoinStatus(GroupJoinStatus.WAIT)
                .memberGroup(group2)
                .denialCount(0)
                .member(requestUser2)
                .build();
        groupJoinService.save(groupJoinRequest4);



    }

}
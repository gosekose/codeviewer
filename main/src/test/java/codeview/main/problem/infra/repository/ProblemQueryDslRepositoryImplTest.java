package codeview.main.problem.infra.repository;

import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.membergroup.infra.repository.membergroup.MemberGroupRepository;
import codeview.main.problem.domain.Problem;
import codeview.main.problem.infra.repository.query.ProblemSearchPageCond;
import codeview.main.problem.infra.repository.query.ProblemSearchPageDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@Transactional
class ProblemQueryDslRepositoryImplTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberGroupRepository memberGroupRepository;
    @Autowired private GroupService groupService;

    @Autowired private ProblemRepository problemRepository;

    @Autowired private ProblemQueryDslRepositoryImpl problemQueryDslRepository;

    @Autowired private GroupStorageService groupStorageService;

    @Test
    public void 문제검색_by멤버Id_creator() throws Exception {

        Member creator = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();

        creator.updateProfile("koseyun", null, null, null, null, null, null);
        memberRepository.save(creator);

        MemberGroup group1 = MemberGroup.builder().creator(creator).maxMember(20)
                .description(String.valueOf(1)).joinClosedTime(LocalDateTime.now())
                .name("TEST1").memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();

        memberGroupRepository.save(group1);

        for(int j=1; j<=2; j++) {
            Problem problem = Problem.builder()
                    .memberGroup(group1).name("problem"+j).build();
            problemRepository.save(problem);
        }

        Member member = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();

        memberRepository.save(member);

        groupStorageService.save(member, group1);


        //given
        ProblemSearchPageCond condition1 = new ProblemSearchPageCond();
        condition1.setMemberId(member.getId());
        condition1.setCreatorName("kww");

        ProblemSearchPageCond condition2 = new ProblemSearchPageCond();
        condition2.setMemberId(member.getId());
        condition2.setCreatorName("ko");

        //when
        List<ProblemSearchPageDto> problemSearchPageDtos1 = problemQueryDslRepository.searchProblem(condition1);
        List<ProblemSearchPageDto> problemSearchPageDtos2 = problemQueryDslRepository.searchProblem(condition2);

        //then
        Assertions.assertThat(problemSearchPageDtos1.size()).isEqualTo(0);
        Assertions.assertThat(problemSearchPageDtos2.size()).isEqualTo(2);
    }



    

    @BeforeEach
    public void dataInit() {

        /**
         * member save
         */
        for(int i=0; i<50; i++) {
            memberRepository.save(Member.builder()
                    .registrationId("NAVER").authorities("MEMBER")
                    .registerId(String.valueOf(i)).picture(UUID.randomUUID().toString())
                    .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString()).build());}

        /**
         * memberGroup save
         */
        Member member = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();

        memberRepository.save(member);

        for(int i=0; i<10; i++) {
            if (i % 4 == 0) {

                MemberGroup group1 = MemberGroup.builder().creator(member).maxMember(20)
                        .description(String.valueOf(i)).joinClosedTime(LocalDateTime.now())
                        .name("TEST1").memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                        .build();

                memberGroupRepository.save(group1);

                for(int j=1; j<=2; j++) {
                    Problem problem = Problem.builder()
                            .memberGroup(group1).name("problem"+j).build();
                    problemRepository.save(problem);
                }

            } else if (i % 4 == 1) {
                MemberGroup group2 = MemberGroup.builder().creator(member)
                        .maxMember(20).description(String.valueOf(i))
                        .joinClosedTime(LocalDateTime.now()).name("TEST1")
                        .memberGroupVisibility(MemberGroupVisibility.HIDDEN)
                        .build();

                memberGroupRepository.save(group2);

                for(int j=1; j<=2; j++) {
                    Problem problem = Problem.builder()
                            .memberGroup(group2).name("problem"+j).build();
                    problemRepository.save(problem);
                }

            } else if (i % 4 == 2) {


                MemberGroup group3 = MemberGroup.builder().creator(member)
                        .maxMember(20).description(String.valueOf(i))
                        .joinClosedTime(LocalDateTime.now()).name("TEST2")
                        .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                        .build();

                memberGroupRepository.save(group3);

                for(int j=1; j<=2; j++) {
                    Problem problem = Problem.builder()
                            .memberGroup(group3).name("problem"+j).build();
                    problemRepository.save(problem);
                }

            } else {
                MemberGroup group4 = MemberGroup.builder().creator(member)
                        .maxMember(20).description(String.valueOf(i))
                        .joinClosedTime(LocalDateTime.now()).name("TEST2")
                        .memberGroupVisibility(MemberGroupVisibility.HIDDEN)
                        .build();

                memberGroupRepository.save(group4);

                for(int j=1; j<=2; j++) {
                    Problem problem = Problem.builder()
                            .memberGroup(group4).name("problem"+j).build();
                    problemRepository.save(problem);
                }
            }
        }
    }
}
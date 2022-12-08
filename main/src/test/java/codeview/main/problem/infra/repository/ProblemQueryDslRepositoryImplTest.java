package codeview.main.problem.infra.repository;

import codeview.main.businessservice.groupstorage.application.GroupStorageService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.MemberRepository;
import codeview.main.businessservice.membergroup.application.GroupService;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.MemberGroupRepository;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.infra.repository.ProblemQueryDslRepositoryImpl;
import codeview.main.businessservice.problem.infra.repository.ProblemRepository;
import codeview.main.businessservice.problem.infra.repository.query.*;
import codeview.main.businessservice.solve.domain.LastSolveStatus;
import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.infra.repository.LastSolveStatusRepository;
import codeview.main.businessservice.solve.infra.repository.SolveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static codeview.main.businessservice.solve.domain.enumtype.SolveStatus.FAIL;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Autowired private SolveRepository solveRepository;

    @Autowired private LastSolveStatusRepository lastSolveStatusRepository;


    @Test
    public void 문제검색_by멤버Id_creator() throws Exception {

        Member creator = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();

        creator.updateProfile("koseyun", null, null, null, null, null);
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
        ProblemSearchPageCondition condition1 = new ProblemSearchPageCondition();
        condition1.setMemberId(member.getId());
        condition1.setCreatorName("kww");

        ProblemSearchPageCondition condition2 = new ProblemSearchPageCondition();
        condition2.setMemberId(member.getId());
        condition2.setCreatorName("ko");

        //when
        List<ProblemSearchPageDto> problemSearchPageDtos1 = problemQueryDslRepository.searchProblem(condition1);
        List<ProblemSearchPageDto> problemSearchPageDtos2 = problemQueryDslRepository.searchProblem(condition2);

        //then
        assertThat(problemSearchPageDtos1.size()).isEqualTo(0);
        assertThat(problemSearchPageDtos2.size()).isEqualTo(2);
    }


    @Test
    public void 인덱스_최근_등록_문제_30개_조회_문제() throws Exception {
        //given

        /**
         * member save // condition
         * problem 10 + 2 // create 시간 확인
         */
        Member member = Member.builder()
                .registrationId("NAVER").authorities("MEMBER")
                .registerId(String.valueOf(1)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString()).build();

        Member saveMember = memberRepository.save(member);

        Member creator = Member.builder()
                .registrationId("NAVER").authorities("MEMBER")
                .registerId(String.valueOf(2)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString()).build();

        memberRepository.save(creator);


        VisibleRecentProblemCondition condition = new VisibleRecentProblemCondition();
        condition.setMemberId(saveMember.getId());

        MemberGroup group = MemberGroup.builder().creator(creator)
                .maxMember(20).description(String.valueOf(1))
                .joinClosedTime(LocalDateTime.now()).name("TEST1")
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();

        memberGroupRepository.save(group);

        Problem problem1 = Problem.builder()
                .memberGroup(group).name("get-1-index").build();
        problemRepository.save(problem1);

        Problem problem2 = Problem.builder()
                .memberGroup(group).name("get-0-index").build();
        problemRepository.save(problem2);


        //when
        List<VisibleRecentProblemDto> visibleRecentProblemDtos = problemQueryDslRepository.searchVisibleRecentProblem(condition);

        //then
        assertThat(visibleRecentProblemDtos.size()).isEqualTo(12);
        assertThat(visibleRecentProblemDtos.get(0).getProblemName()).isEqualTo(problem2.getName());
        assertThat(visibleRecentProblemDtos.get(1).getProblemName()).isEqualTo(problem1.getName());
        assertThat(visibleRecentProblemDtos.get(0).getSolveStatus()).isNull();
    }


    @Test
    public void 로그인X_최근_등록_문제_30개_조회_문제() throws Exception {
        //given
        VisibleRecentProblemCondition condition = new VisibleRecentProblemCondition();

        //when
        List<VisibleRecentProblemNoLoginDto> visibleRecentProblemNoLoginDtos = problemQueryDslRepository.searchVisibleRecentProblemNoLogin(condition);

        //then
        assertThat(visibleRecentProblemNoLoginDtos.size()).isEqualTo(10);
    }


    @Test
    public void 로그인X_최근_등록_문제_30개_조회_문제_정렬_확인() throws Exception {
        //given
        VisibleRecentProblemCondition condition = new VisibleRecentProblemCondition();


        Member creator = Member.builder()
                .registrationId("NAVER").authorities("MEMBER")
                .registerId(String.valueOf(2)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString()).build();

        memberRepository.save(creator);

        MemberGroup group = MemberGroup.builder().creator(creator)
                .maxMember(20).description(String.valueOf(1))
                .joinClosedTime(LocalDateTime.now()).name("TEST1")
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();

        memberGroupRepository.save(group);

        Problem problem1 = Problem.builder()
                .memberGroup(group).name("get-1-index").build();
        problemRepository.save(problem1);

        Problem problem2 = Problem.builder()
                .memberGroup(group).name("get-0-index").build();
        problemRepository.save(problem2);

        //when
        List<VisibleRecentProblemNoLoginDto> visibleRecentProblemNoLoginDtos = problemQueryDslRepository.searchVisibleRecentProblemNoLogin(condition);

        //then
        assertThat(visibleRecentProblemNoLoginDtos.size()).isEqualTo(12);
        assertThat(visibleRecentProblemNoLoginDtos.get(0).getProblemName()).isEqualTo(problem2.getName());
        assertThat(visibleRecentProblemNoLoginDtos.get(1).getProblemName()).isEqualTo(problem1.getName());
    }

    @Test
    public void 인덱스_최근_등록_문제_30개_조회_문제_last_solve_존재() throws Exception {
        VisibleRecentProblemCondition condition = new VisibleRecentProblemCondition();

        Member creator = Member.builder()
                .registrationId("NAVER").authorities("MEMBER")
                .registerId(String.valueOf(2)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString()).build();

        memberRepository.save(creator);

        MemberGroup group = MemberGroup.builder().creator(creator)
                .maxMember(20).description(String.valueOf(1))
                .joinClosedTime(LocalDateTime.now()).name("TEST1")
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .build();

        memberGroupRepository.save(group);

        Problem problem1 = Problem.builder()
                .memberGroup(group).name("get-1-index").build();

        problemRepository.save(problem1);

        Member solver = Member.builder()
                .registrationId("NAVER").authorities("MEMBER")
                .registerId(String.valueOf(3)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString()).build();

        memberRepository.save(solver);

        int countSolveByMember = solveRepository.getCountSolve(problem1, solver);
        Solve solve3 = Solve.builder()
                .member(solver)
                .number(countSolveByMember)
                .score(70)
                .solveStatus(FAIL)
                .problem(problem1)
                .build();

        solveRepository.save(solve3);

        LastSolveStatus saveSolveStatusByMember = lastSolveStatusRepository.save(
                LastSolveStatus.builder()
                        .member(solver)
                        .problem(problem1)
                        .solveStatus(solve3.getSolveStatus())
                        .build()
        );

        countSolveByMember = solveRepository.getCountSolve(problem1, solver);
        Solve solve4 = Solve.builder()
                .member(solver)
                .number(countSolveByMember)
                .score(90)
                .solveStatus(FAIL)
                .problem(problem1)
                .build();

        solveRepository.save(solve4);
        saveSolveStatusByMember.updateSolveStatus(solve4.getSolveStatus());

        condition.setMemberId(solver.getId());

        //when
        List<VisibleRecentProblemDto> visibleRecentProblemDtos = problemQueryDslRepository.searchVisibleRecentProblem(condition);

        //then
        assertThat(visibleRecentProblemDtos.size()).isEqualTo(11);
        assertThat(visibleRecentProblemDtos.get(0).getProblemId()).isEqualTo(problem1.getId());
        assertThat(visibleRecentProblemDtos.get(0).getSolveStatus()).isEqualTo(FAIL);
        assertThat(visibleRecentProblemDtos.get(8).getSolveStatus()).isNull();

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
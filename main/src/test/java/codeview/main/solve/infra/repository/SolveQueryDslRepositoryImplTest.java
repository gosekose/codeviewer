package codeview.main.solve.infra.repository;

import codeview.main.businessservice.groupstorage.application.GroupStorageService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.MemberRepository;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.MemberGroupRepository;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.infra.repository.ProblemRepository;
import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.infra.repository.SolveQueryDslRepositoryImpl;
import codeview.main.businessservice.solve.infra.repository.SolveRepository;
import codeview.main.businessservice.solve.infra.repository.query.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SolveQueryDslRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired private MemberGroupRepository memberGroupRepository;

    @Autowired private ProblemRepository problemRepository;

    @Autowired private SolveRepository solveRepository;

    @Autowired private GroupStorageService groupStorageService;

    @Autowired private SolveQueryDslRepositoryImpl solveQueryDslRepository;

    static Long creatorId;
    static Long problemId;
    static Long solveMemberId;
    static Long groupId;

    static Long problemIdOfNoSolve;

    @Test
    public void 해결검색_by_problemId() throws Exception {
//
        //given
        SolvesOfProblemCondition condition = new SolvesOfProblemCondition();
        condition.setProblemId(problemId);

        //when
        List<Solve> solves = solveQueryDslRepository.searchAllSolve(condition);

        //then
        assertThat(solves.size()).isEqualTo(3);
    }

    @Test
    public void 해결검색_by_해결한_memberId() throws Exception {
        //given
        SolvesOfProblemCondition condition = new SolvesOfProblemCondition();
        condition.setMemberId(solveMemberId);

        //when
        List<Solve> solves = solveQueryDslRepository.searchAllSolve(condition);

        //then
        assertThat(solves.size()).isEqualTo(12);
    }

    @Test
    public void 해결검색_by_problemId_and_memberId() throws Exception {
        //given
        SolvesOfProblemCondition condition = new SolvesOfProblemCondition();
        condition.setProblemId(problemId);
        condition.setMemberId(solveMemberId);

        //when
        List<Solve> solves = solveQueryDslRepository.searchAllSolve(condition);

        //then
        assertThat(solves.size()).isEqualTo(3);
    }

    @Test
    public void 해결검색_by_problemId_and_memberId_90점이상() throws Exception {
        //given
        SolvesOfProblemCondition condition = new SolvesOfProblemCondition();
        condition.setProblemId(problemId);
        condition.setMemberId(solveMemberId);
        condition.setGoeScore(90);

        //when
        List<Solve> solves = solveQueryDslRepository.searchAllSolve(condition);

        //then
        assertThat(solves.size()).isEqualTo(1);
    }

    @Test
    public void 해결검색_by_problemId_and_memberId_90점이하() throws Exception {
        //given
        SolvesOfProblemCondition condition = new SolvesOfProblemCondition();
        condition.setProblemId(problemId);
        condition.setMemberId(solveMemberId);
        condition.setLoeScore(90);

        //when
        List<Solve> solves = solveQueryDslRepository.searchAllSolve(condition);

        //then
        assertThat(solves.size()).isEqualTo(3);
    }

    @Test
    public void 해결검색_해결X() throws Exception {
        //given
        SolvesOfProblemCondition condition = new SolvesOfProblemCondition();
        condition.setProblemId(problemIdOfNoSolve);

        //when
        List<Solve> solves = solveQueryDslRepository.searchAllSolve(condition);

        //then
        assertThat(solves.size()).isEqualTo(0);

    }

    @Test
    public void 그룹회원_info_by_memberId_groupId() throws Exception {
        //given
        MemberSolveInfoCondition condition = new MemberSolveInfoCondition();
        condition.setGroupId(groupId);
        condition.setMemberId(solveMemberId);

        //when
        List<MemberSolveInfoDto> memberSolveInfoDtos = solveQueryDslRepository.searchMemberSolvesNoProblemId(condition);

        //then
        assertThat(memberSolveInfoDtos.size()).isEqualTo(2);
        assertThat(memberSolveInfoDtos.get(0).getMaxScore()).isEqualTo(90);
    }
    /**
     * searchMemberSolves
     */
    @Test
    public void 그룹회원_info_by_memberId_groupId_problemId() throws Exception {
        //given
        MemberSolveInfoCondition condition = new MemberSolveInfoCondition();
        condition.setGroupId(groupId);
        condition.setMemberId(solveMemberId);
        condition.setProblemId(problemId);

        //when
        List<MemberSolveInfoDto> memberSolveInfoDtos = solveQueryDslRepository.searchMemberSolvesByProblemId(condition);

        //then
        assertThat(memberSolveInfoDtos.size()).isEqualTo(3);
        assertThat(memberSolveInfoDtos.get(0).getLastModifiedTime()).isBefore(memberSolveInfoDtos.get(1).getLastModifiedTime());
        assertThat(memberSolveInfoDtos.get(0).getMaxScore()).isEqualTo(30);

    }

    @Test
    public void 내스코어_평균() throws Exception {
        //given
        MemberSolveInfoCondition condition = new MemberSolveInfoCondition();
        condition.setProblemId(problemId);
        condition.setMemberId(solveMemberId);

        //when
        List<SolvesOfProblemChartMyScoreDto> solvesOfProblemChartMyScoreDtos = solveQueryDslRepository.searchMemberSolvesMyChartDto(condition);

        //then
        assertThat(solvesOfProblemChartMyScoreDtos.size()).isEqualTo(3);

    }

    @Test
    public void 다른스코어_평균() throws Exception {
        //given
        MemberSolveInfoCondition condition = new MemberSolveInfoCondition();
        condition.setProblemId(problemId);
        condition.setSolveCount(2);

        //when
        List<SolvesOfProblemChartOtherScoreDto> solvesOfProblemChartOtherScoreDtos = solveQueryDslRepository.searchMemberSolvesOtherChartDto(condition);

        //then
        assertThat(solvesOfProblemChartOtherScoreDtos.size()).isEqualTo(2);
        assertThat(solvesOfProblemChartOtherScoreDtos.get(0).getNumber()).isEqualTo(0);
        assertThat(solvesOfProblemChartOtherScoreDtos.get(1).getNumber()).isEqualTo(1);
        assertThat(solvesOfProblemChartOtherScoreDtos.get(1).getOtherScore()).isEqualTo(60.0);


    }



    @BeforeEach
    public void doinit() {

        /**
         *
         * member => creator (그룹 개설자 + 문제 출제자)
         * solveMember => creator가 낸 문제 해결한 사람
         * newSolveMember => creator가 낸 문제를 해결한 다른 사람
         *
         * group1
         * 회원: solveMember
         * 문제: problem1, problem2
         * 정답: 문제 당 sovle1, solve2, sovle3 => 6개
         *
         * group2
         * 회원: solveMember
         * 문제: problem1, problem2
         * 정답: 문제 당 sovle1, solve2, sovle3 => 6개
         *
         *
         * group3
         * 회원: newSolveMember
         * 문제: problem1, problem2
         * 정답: 문제 당 sovle1, solve2, sovle3 => 6개
         *
         *
         * group4
         * 회원: 없음
         * 문제: problem1, problem2
         */

        Member member = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();

        memberRepository.save(member);
        creatorId = member.getId();

        Member solveMember = Member.builder()
                .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();

        memberRepository.save(solveMember);
        solveMemberId = solveMember.getId();

        Member newSolveMember = Member.builder()
                        .registerId(String.valueOf(10)).picture(UUID.randomUUID().toString())
                        .password(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                        .build();

        memberRepository.save(newSolveMember);

        for(int i=0; i<4; i++) {
            if (i == 0) {

                MemberGroup group1 = MemberGroup.builder().creator(member).maxMember(20)
                        .description(String.valueOf(i)).joinClosedTime(LocalDateTime.now())
                        .name("TEST1").memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                        .build();

                memberGroupRepository.save(group1);

                groupStorageService.save(solveMember, group1);
                groupId = group1.getId();

                for(int j=1; j<=2; j++) {
                    Problem problem = Problem.builder()
                            .memberGroup(group1).name("problem"+j).build();
                    problemRepository.save(problem);
                    problemId = problem.getId();

                    for(int k=1; k<=3; k++) {
                        Solve solve = Solve.builder().member(solveMember).number(k-1).problem(problem).score(k * 30).build();
                        solveRepository.save(solve);
                    }
                }

            } else if (i == 1) {
                MemberGroup group2 = MemberGroup.builder().creator(member)
                        .maxMember(20).description(String.valueOf(i))
                        .joinClosedTime(LocalDateTime.now()).name("TEST1")
                        .memberGroupVisibility(MemberGroupVisibility.HIDDEN)
                        .build();

                memberGroupRepository.save(group2);

                groupStorageService.save(solveMember, group2);

                for(int j=1; j<=2; j++) {
                    Problem problem = Problem.builder()
                            .memberGroup(group2).name("problem"+j).build();
                    problemRepository.save(problem);

                    for(int k=1; k<=3; k++) {
                        Solve solve = Solve.builder().member(solveMember).problem(problem).score(k * 30).build();
                        solveRepository.save(solve);
                    }
                }


            } else if (i == 2) {


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

                    for(int k=1; k<=3; k++) {
                        Solve solve = Solve.builder().member(newSolveMember).problem(problem).score(k * 30).build();
                        solveRepository.save(solve);
                    }
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
                    problemIdOfNoSolve = problem.getId();
                }
            }


        }

    }

}
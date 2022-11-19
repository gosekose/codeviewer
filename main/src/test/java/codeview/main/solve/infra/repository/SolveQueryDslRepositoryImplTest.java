package codeview.main.solve.infra.repository;

import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.member.domain.Member;
import codeview.main.member.infra.MemberRepository;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.membergroup.infra.repository.MemberGroupRepository;
import codeview.main.problem.domain.Problem;
import codeview.main.problem.infra.repository.ProblemRepository;
import codeview.main.solve.domain.Solve;
import codeview.main.solve.infra.repository.query.SolvesOfProblemCondition;
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

    @Test
    public void 해결검색_by_problemId() throws Exception {
//
        //given
        SolvesOfProblemCondition condition = new SolvesOfProblemCondition();
        condition.setProblemId(problemId);

        //when
        List<Solve> solves = solveQueryDslRepository.searchAllSolve(condition);

        //then
        Assertions.assertThat(solves.size()).isEqualTo(3);
    }

    @Test
    public void 해결검색_by_해결한_memberId() throws Exception {
        //given
        SolvesOfProblemCondition condition = new SolvesOfProblemCondition();
        condition.setMemberId(solveMemberId);

        //when
        List<Solve> solves = solveQueryDslRepository.searchAllSolve(condition);

        //then
        Assertions.assertThat(solves.size()).isEqualTo(12);
    }

    @BeforeEach
    public void doinit() {

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
            if (i % 4 == 0) {

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
                        Solve solve = Solve.builder().member(solveMember).problem(problem).score(k * 30).build();
                        solveRepository.save(solve);
                    }
                }

            } else if (i % 4 == 1) {
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
                }
            }


        }

    }

}
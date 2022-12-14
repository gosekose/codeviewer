package codeview.main.businessservice.temp;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.application.GroupService;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.MemberGroupRepository;
import codeview.main.businessservice.problem.application.ProblemScoreService;
import codeview.main.businessservice.problem.application.ProblemService;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.domain.ProblemScore;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import codeview.main.businessservice.problem.domain.enumtype.ProblemType;
import codeview.main.businessservice.problemdescription.application.ProblemDescriptionService;
import codeview.main.businessservice.problemdescription.application.ProblemIoExampleService;
import codeview.main.businessservice.problemdescription.domain.ProblemDescription;
import codeview.main.businessservice.problemdescription.domain.ProblemIoExample;
import codeview.main.businessservice.solve.domain.LastSolveStatus;
import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.domain.enumtype.SolveStatus;
import codeview.main.businessservice.solve.infra.repository.LastSolveStatusRepository;
import codeview.main.businessservice.solve.infra.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TempController {

    private final MemberService memberService;
    private final GroupService groupService;
    private final ProblemService problemService;

    private final MemberGroupRepository memberGroupRepository;
    private final ProblemIoExampleService problemIoExampleService;
    private final ProblemDescriptionService problemDescriptionService;
    private final ProblemScoreService problemScoreService;

    private final SolveRepository solveRepository;

    private final LastSolveStatusRepository lastSolveStatusRepository;

    static Member solveMember;

    @Transactional
    @GetMapping("/api/v1/temp/admin/create/group")
    public String createDate(@AuthenticationPrincipal PrincipalUser principalUser, Model model) {

        /**
         *
         * 해당 클라이언트에서 group 생성
         * 15개의 그룹 생성 10개 visible, 5개 hidden
         * 한 그룹 당 10명의 member 가입
         *
         *
         */
        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());

        MemberGroup memberGroup = MemberGroup.builder()
                .creator(member)
                .maxMember(10)
                .description("")
                .name("test1")
                .joinClosedTime(LocalDateTime.now())
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .groupAutoJoin(GroupAutoJoin.ON)
                .build();

        memberGroupRepository.save(memberGroup);

        solveMember = memberService.findByEmail("1@naver.com");

        Problem problem = Problem.builder()
                .name("problem1-test")
                .memberGroup(memberGroup)
                .problemDifficulty(ProblemDifficulty.BRONZE1)
                .problemInputIoFile(new ProblemInputIoFile(UUID.randomUUID().toString(), "add.zip"))
                .build();

        problemService.save(problem);


        MemberGroup memberGroup1 = MemberGroup.builder()
                .creator(solveMember)
                .maxMember(10)
                .description("")
                .name("문제 TEST")
                .joinClosedTime(LocalDateTime.now())
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .groupAutoJoin(GroupAutoJoin.ON)
                .build();

        memberGroupRepository.save(memberGroup1);

        Problem problem2 = Problem.builder()
                .name("내림차순 정렬")
                .memberGroup(memberGroup1)
                .totalScore(100)
                .openTime(LocalDateTime.now())
                .closedTime(LocalDateTime.parse("2022-12-20T11:19:03"))
                .problemType(ProblemType.TESTTYPE)
                .problemDifficulty(ProblemDifficulty.GOLD1)
                .problemInputIoFile(new ProblemInputIoFile(UUID.randomUUID().toString(), "sort.zip"))
                .problemLanguage("python3&java11")
                .build();

        problemService.save(problem2);

        problemIoExampleService.save(ProblemIoExample.builder().inputSource("3 1 2 3")
                .outputSource("3 2 1")
                .number(1)
                .problem(problem2)
                .build());

        problemIoExampleService.save(ProblemIoExample.builder().inputSource("5 1 2 3 4 5")
                .outputSource("5 4 3 2 1")
                .number(2)
                .problem(problem2)
                .build());


        problemDescriptionService.save(ProblemDescription.builder()
                .description("내림차순 정렬 하시오")
                .problem(problem2)
                .number(1)
                .build());

        problemScoreService.save(ProblemScore.builder()
                .number(1)
                .score(50)
                .problem(problem2)
                .build());

        problemScoreService.save(ProblemScore.builder()
                .number(2)
                .score(50)
                .problem(problem2)
                .build());


        int countSolveBySolver = solveRepository.getCountSolve(problem, solveMember);
        Solve solve1 = Solve.builder()
                .member(solveMember)
                .number(countSolveBySolver)
                .score(70)
                .solveStatus(SolveStatus.FAIL)
                .problem(problem)
                .build();

        solveRepository.save(solve1);

        LastSolveStatus saveSolveStatusBySolver = lastSolveStatusRepository.save(
                LastSolveStatus.builder()
                        .member(solveMember)
                        .problem(problem)
                        .solveStatus(solve1.getSolveStatus())
                        .build()
        );

        countSolveBySolver = solveRepository.getCountSolve(problem, solveMember);
        Solve solve2 = Solve.builder()
                .member(solveMember)
                .number(countSolveBySolver)
                .score(100)
                .solveStatus(SolveStatus.SUCCESS)
                .problem(problem)
                .build();

        solveRepository.save(solve2);
        saveSolveStatusBySolver.updateSolveStatus(solve2.getSolveStatus());

        MemberGroup memberGroup2 = MemberGroup.builder()
                .creator(solveMember)
                .maxMember(21)
                .description("")
                .name("IISE")
                .joinClosedTime(LocalDateTime.now())
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .groupAutoJoin(GroupAutoJoin.ON)
                .build();

        memberGroupRepository.save(memberGroup2);

        for(int i=0; i<50; i++) {

            Problem problemTest = Problem.builder()
                    .name("test" + (i + 10))
                    .memberGroup(memberGroup2)
                    .problemDifficulty(ProblemDifficulty.GOLD2)
                    .problemInputIoFile(new ProblemInputIoFile(UUID.randomUUID().toString(), "add.zip"))
                    .build();

            problemService.save(problemTest);



            if (i>40) {

                SolveStatus solveStatus;

                if (Math.random() > 0.5) {
                    solveStatus = SolveStatus.SUCCESS;
                } else {
                    solveStatus = SolveStatus.FAIL;
                }

                int countSolveByMemberTest = solveRepository.getCountSolve(problemTest, member);
                Solve solveTest = Solve.builder()
                        .member(member)
                        .number(countSolveByMemberTest)
                        .score(70)
                        .solveStatus(solveStatus)
                        .problem(problemTest)
                        .build();

                solveRepository.save(solveTest);

                LastSolveStatus lastSolveStatus = LastSolveStatus.builder()
                        .member(member)
                        .problem(problemTest)
                        .solveStatus(solveTest.getSolveStatus())
                        .build();

                lastSolveStatusRepository.save(lastSolveStatus);
            }

        }

        return "redirect:/";

    }

}

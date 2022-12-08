package codeview.main.businessservice.temp;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.businessservice.groupstorage.application.GroupStorageService;
import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.application.GroupService;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.MemberGroupRepository;
import codeview.main.businessservice.problem.application.ProblemService;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import codeview.main.businessservice.solve.domain.LastSolveStatus;
import codeview.main.businessservice.solve.domain.Solve;
import codeview.main.businessservice.solve.domain.enumtype.SolveStatus;
import codeview.main.businessservice.solve.infra.repository.LastSolveStatusRepository;
import codeview.main.businessservice.solve.infra.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
    private final GroupStorageService groupStorageService;

    private final SolveRepository solveRepository;

    private final LastSolveStatusRepository lastSolveStatusRepository;

    static Member solveMember;

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
                .name("iise-test")
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
                .problemInputIoFile(new ProblemInputIoFile(UUID.randomUUID().toString()))
                .build();

        problemService.save(problem);

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

        MemberGroup memberGroup1 = MemberGroup.builder()
                .creator(solveMember)
                .maxMember(21)
                .description("")
                .name("iise-test4")
                .joinClosedTime(LocalDateTime.now())
                .memberGroupVisibility(MemberGroupVisibility.VISIBLE)
                .groupAutoJoin(GroupAutoJoin.ON)
                .build();

        memberGroupRepository.save(memberGroup1);

        for(int i=0; i<50; i++) {
            problemService.save(Problem.builder()
                    .name("test"+ (i+10))
                    .memberGroup(memberGroup1)
                    .problemDifficulty(ProblemDifficulty.GOLD2)
                    .problemInputIoFile(new ProblemInputIoFile(UUID.randomUUID().toString()))
                    .build());
        }

        Problem problem1 = Problem.builder()
                .name("test1")
                .memberGroup(memberGroup1)
                .problemDifficulty(ProblemDifficulty.GOLD1)
                .problemInputIoFile(new ProblemInputIoFile(UUID.randomUUID().toString()))
                .build();

        problemService.save(problem1);

        int countSolveByMember = solveRepository.getCountSolve(problem1, member);
        Solve solve3 = Solve.builder()
                .member(member)
                .number(countSolveByMember)
                .score(70)
                .solveStatus(SolveStatus.FAIL)
                .problem(problem1)
                .build();

        solveRepository.save(solve3);

        LastSolveStatus saveSolveStatusByMember = lastSolveStatusRepository.save(
                LastSolveStatus.builder()
                        .member(member)
                        .problem(problem1)
                        .solveStatus(solve3.getSolveStatus())
                        .build()
        );

        countSolveByMember = solveRepository.getCountSolve(problem1, member);
        Solve solve4 = Solve.builder()
                .member(member)
                .number(countSolveByMember)
                .score(90)
                .solveStatus(SolveStatus.SUCCESS)
                .problem(problem1)
                .build();

        solveRepository.save(solve4);
        saveSolveStatusByMember.updateSolveStatus(solve4.getSolveStatus());

        log.info("member id = {}", member.getId());
        log.info("memberGroup 저장 완료");

        return "redirect:/";

    }

}

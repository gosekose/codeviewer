package codeview.main.temp;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.membergroup.infra.repository.MemberGroupRepository;
import codeview.main.problem.application.ProblemService;
import codeview.main.problem.domain.Problem;
import codeview.main.solve.domain.Solve;
import codeview.main.solve.infra.repository.SolveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

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

    static Member solveMember1;
    static Member solveMember2;

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

        String[] groupNames = new String[] {"if else", "for", "예외처리", "while", "자료구조", "알고리즘", "dp", "파일 읽기"};

        int randomName = 0;

        for(int i=0; i <5; i++) {

            randomName = (int) (Math.random() * 7);

            MemberGroupVisibility visibility = MemberGroupVisibility.VISIBLE;
            GroupAutoJoin join = GroupAutoJoin.ON;

            if (i > 2) {
                visibility = MemberGroupVisibility.HIDDEN;
                join = GroupAutoJoin.OFF;
            }

            MemberGroup memberGroup = MemberGroup.builder()
                    .creator(member)
                    .maxMember(i + 20)
                    .description("")
                    .name(groupNames[randomName])
                    .joinClosedTime(LocalDateTime.now())
                    .memberGroupVisibility(visibility)
                    .groupAutoJoin(join)
                    .build();

            memberGroupRepository.save(memberGroup);

            for (int j = 0; j <= 5; j++) {
                Member members = memberService.findByEmail(j + "@naver.com");

                groupStorageService.save(members, memberGroup);

                if (j == 0) {
                    solveMember1 = members;
                }
                if (j == 5) {
                    solveMember2 = members;
                }
            }

            for (int k = 0; k < 5; k++) {

                Problem problem = Problem.builder().name("test" + k)
                        .memberGroup(memberGroup).build();

                problemService.save(problem);

                for (int s = 0; s < 5; s++) {
                    int countSolve1 = solveRepository.getCountSolve(problem, solveMember1);
                    int countSolve2 = solveRepository.getCountSolve(problem, solveMember2);
                    Solve solve1 = Solve.builder()
                            .member(solveMember1)
                            .number(countSolve1)
                            .score(s * 15 + ((int) (Math.random() * 25)))
                            .problem(problem)
                            .build();

                    Solve solve2 = Solve.builder()
                            .member(solveMember2)
                            .number(countSolve2)
                            .score(s * 20 + ((int) (Math.random() * 25)))
                            .problem(problem)
                            .build();

                    solveRepository.save(solve1);
                    solveRepository.save(solve2);

                }

            }
        }

        log.info("member id = {}", member.getId());
        log.info("memberGroup 저장 완료");


        return "redirect:/";

    }

}

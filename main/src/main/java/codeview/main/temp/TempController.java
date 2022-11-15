package codeview.main.temp;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.MemberGroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.MemberGroupVisibility;
import codeview.main.membergroup.infra.repository.MemberGroupRepository;
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
    private final MemberGroupService memberGroupService;
    private final MemberGroupRepository memberGroupRepository;

    @GetMapping("/api/v1/temp/admin/create/group")
    public String createDate(@AuthenticationPrincipal PrincipalUser principalUser, Model model) {

        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());

        String[] groupNames = new String[] {"if else", "for", "예외처리", "while", "자료구조", "알고리즘", "dp", "파일 읽기"};

        int randomName = 0;

        for(int i=0; i <100; i++) {

            randomName = (int) (Math.random() * 7);

            MemberGroupVisibility visibility = MemberGroupVisibility.VISIBLE;

            if (i > 80) {
                visibility = MemberGroupVisibility.HIDDEN;
            }

            memberGroupRepository.save(MemberGroup.builder()
                            .member(member)
                            .maxMember(i+20)
                            .description("")
                            .name(groupNames[randomName])
                            .joinClosedTime(LocalDateTime.now())
                            .memberGroupVisibility(visibility)
                            .build());
        }

        log.info("member id = {}", member.getId());
        log.info("memberGroup 저장 완료");


        return "redirect:/";

    }

}
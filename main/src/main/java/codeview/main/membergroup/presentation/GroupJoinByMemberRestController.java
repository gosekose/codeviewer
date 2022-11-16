package codeview.main.membergroup.presentation;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gruops/{groupId}/join")
@RequiredArgsConstructor
public class GroupJoinByMemberRestController {

    private final MemberService memberService;

    @PostMapping
    private ResponseEntity<BaseEntity> postJoinGroup(@PathVariable("groupId") Integer groupId,
                                                     @AuthenticationPrincipal PrincipalUser principalUser) {

        String id = principalUser.getProviderUser().getId();
        Member member = memberService.findByRegisterId(id);


        return null;

    }

}

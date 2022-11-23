package codeview.main.membergroup.presentation.controller.admin.join;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupJoinService;
import codeview.main.membergroup.infra.repository.join.query.JoinRequestCondition;
import codeview.main.membergroup.infra.repository.join.query.JoinRequestQueryPageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/api/v1/groups/admin/{groupId}/join")
@RequiredArgsConstructor
public class GroupJoinAdminUiController {

    private final MemberService memberService;
    private final GroupJoinService groupJoinService;

    @GetMapping
    public String responseJoinRequest(
            @PathVariable("groupId") String groupId,
            @AuthenticationPrincipal PrincipalUser principalUser,
            JoinRequestCondition condition,
            @PageableDefault Pageable pageable,
            Model model) throws Exception {

        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());
        condition.setMember(member);

        Page<JoinRequestQueryPageDto> joinRequestQueryPageDto = groupJoinService.findJoinRequestQueryPageDto(condition, pageable);

        model.addAttribute("groupId", groupId);
        model.addAttribute("joinRequestQueryPageDto", joinRequestQueryPageDto);

        return "groups/admins/my-member-join-request-list";
    }
}

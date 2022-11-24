package codeview.main.membergroup.presentation.controller.user.join;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.groupstorage.infra.repository.query.list.GroupStorageListCondition;
import codeview.main.groupstorage.infra.repository.query.list.GroupStorageListDto;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/v1/groups/join/list")
@RequiredArgsConstructor
public class MemberUiController {

    private final MemberService memberService;
    private final GroupStorageService groupStorageService;
    @GetMapping
    public String getJoinList(Model model,
                              GroupStorageListCondition condition,
                              @PageableDefault Pageable pageable,
                              @AuthenticationPrincipal PrincipalUser principalUser) {

        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());
        condition.setMember(member);

        Page<GroupStorageListDto> groupStorageListForMember = groupStorageService.getGroupStorageListForMember(condition, pageable);
        model.addAttribute("groupList", groupStorageListForMember);

        return "groups/users/my-join-groups";
    }
}

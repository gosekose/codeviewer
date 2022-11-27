package codeview.main.membergroup.presentation.controller.admin.create;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.common.presentation.page.PageUtils;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.application.GroupsGetMemberPageService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.repository.membergroup.query.MemberGroupSearchCondition;
import codeview.main.membergroup.presentation.dto.GroupForPageDto;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Slf4j
public class GroupListController {

    private final GroupsGetMemberPageService groupsGetMemberPageService;
    private final MemberService memberService;

    private final GroupService groupService;

    @GetMapping
    public String index(Model model) {

        return "groups/my-groups";
    }


    @GetMapping("/admin/list")
    public String getGroupList(Model model,
                               MemberGroupSearchCondition condition,
                               @AuthenticationPrincipal PrincipalUser principalUser,
                               @PageableDefault Pageable pageable) {

        Member admin = memberService.findByRegisterId(principalUser.getProviderUser().getId());
        condition.setAdmin(admin);

        Page<GroupForPageDto> memberGroupsPage = groupsGetMemberPageService.getMyMemberGroupsPage(condition, pageable);
        PageUtils.modelPagingAndModel(memberGroupsPage, model, "memberGroups");

//        MemberGroupsPageUtil.modelPagingAndModel(memberGroupsPage, model);

        return "groups/admins/my-group-list";
    }


    @GetMapping("/admin/{groupId}")
    public String getGroupDetail(
            Model model, @PathVariable("groupId") Integer groupId,
            @AuthenticationPrincipal PrincipalUser principalUser) {

        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));

        GroupForPageDto groupForPageDto = GroupForPageDto.builder()
                .id(memberGroup.getId())
                .name(memberGroup.getName())
                .maxMember(memberGroup.getMaxMember())
                .visibility(memberGroup.getMemberGroupVisibility())
                .joinClosedTime(memberGroup.getJoinClosedTime())
                .build();

        model.addAttribute("memberGroupDto", groupForPageDto);
        model.addAttribute("groupId", groupId);

        log.info("groupForPageDto = {}", groupForPageDto);

        return "problems/admins/my-problems";
    }


    @GetMapping("/admin/errors")
    public String getError(@RequestParam("status") String status) {

        log.info("status = {}", status);

        if (status.equals("403")) {
            return "errors/403";
        }

        return "errors/404";

    }

}

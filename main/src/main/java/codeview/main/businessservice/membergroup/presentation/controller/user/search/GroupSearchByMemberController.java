package codeview.main.businessservice.membergroup.presentation.controller.user.search;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.businessservice.membergroup.application.GroupsGetMemberPageService;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.query.MemberGroupSearchCondition;
import codeview.main.businessservice.membergroup.presentation.dto.GroupForPageDto;
import codeview.main.common.application.CsrfProviderService;
import codeview.main.common.presentation.page.PageUtils;
import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/search")
public class GroupSearchByMemberController {

    private final GroupsGetMemberPageService groupsGetMemberPageService;
    private final MemberService memberService;
    private final CsrfProviderService csrfProviderService;


    @GetMapping
    public String getGroupsSearchPage(Model model,
                                      @AuthenticationPrincipal PrincipalUser principalUser,
                                      HttpServletRequest request,
                                      MemberGroupSearchCondition condition,
                                      @PageableDefault Pageable pageable) {

        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());

        condition.setVisibility(MemberGroupVisibility.VISIBLE);
        condition.setCreator(member);

        Page<GroupForPageDto> memberGroupsPage = groupsGetMemberPageService.getSearchGroupByJoinStatus(condition, pageable);

//        MemberGroupsPageUtil.modelPagingAndModel(memberGroupsPage, model);

        PageUtils.modelPagingAndModel(memberGroupsPage, model, "memberGroups");
        model.addAttribute("_csrf", csrfProviderService.createCsrf(request));
        model.addAttribute("memberId", member.getId());

        return "groups/search-groups";
    }

}

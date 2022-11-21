package codeview.main.membergroup.presentation.controller;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.common.application.CsrfProviderService;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.MemberGroupsPageService;
import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.membergroup.presentation.dao.MemberGroupSearchCondition;
import codeview.main.membergroup.presentation.dto.GroupForPageDto;
import codeview.main.membergroup.presentation.util.MemberGroupsPageUtil;
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

    private final MemberGroupsPageService memberGroupsPageService;
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

        Page<GroupForPageDto> memberGroupsPage = memberGroupsPageService.getMemberGroupsPage(condition, pageable);

        MemberGroupsPageUtil.modelPagingAndModel(memberGroupsPage, model);
        model.addAttribute("_csrf", csrfProviderService.createCsrf(request));
        model.addAttribute("memberId", member.getId());

        return "groups/search-groups";
    }

}

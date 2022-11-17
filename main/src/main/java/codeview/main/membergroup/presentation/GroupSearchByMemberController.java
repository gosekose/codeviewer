package codeview.main.membergroup.presentation;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/search")
public class GroupSearchByMemberController {

    private final MemberGroupsPageService memberGroupsPageService;

    @GetMapping
    public String getGroupsSearchPage(Model model,
                                      MemberGroupSearchCondition condition,
                                      @PageableDefault Pageable pageable) {

        condition.setVisibility(MemberGroupVisibility.VISIBLE);

        Page<GroupForPageDto> memberGroupsPage = memberGroupsPageService.getMemberGroupsPage(condition, pageable);

        MemberGroupsPageUtil.modelPagingAndModel(memberGroupsPage, model);

        return "groups/search-groups";
    }

}

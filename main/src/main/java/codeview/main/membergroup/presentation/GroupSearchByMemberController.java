package codeview.main.membergroup.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
import codeview.main.membergroup.infra.repository.MemberGroupQueryDslRepository;
import codeview.main.membergroup.presentation.dao.MemberGroupSearchCondition;
import codeview.main.membergroup.presentation.dto.GroupForPageDto;
import codeview.main.membergroup.presentation.util.MemberGroupsPage;
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

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/search")
public class GroupSearchByMemberController {

    private final MemberGroupQueryDslRepository memberGroupQueryDslRepository;

    @GetMapping
    public String getGroupsSearchPage(Model model,
                                      MemberGroupSearchCondition condition,
                                      @AuthenticationPrincipal PrincipalUser principalUser,
                                      @PageableDefault Pageable pageable) {

        condition.setVisibility(MemberGroupVisibility.VISIBLE);

        Page<GroupForPageDto> memberGroupDtos = MemberGroupsPage.getMemberGroupsPage(memberGroupQueryDslRepository, condition, pageable, model);

        log.info("memberGroupDtos.getTotalPages() = {}", ((Page<?>) memberGroupDtos).getTotalPages());
        log.info("memberGroupDtos.getTotalElements() = {}", memberGroupDtos.getTotalElements());


        return "groups/search-groups";
    }

}

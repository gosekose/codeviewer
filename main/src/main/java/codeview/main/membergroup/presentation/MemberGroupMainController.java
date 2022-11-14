package codeview.main.membergroup.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.MemberGroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.dao.MemberGroupSearchCondition;
import codeview.main.membergroup.infra.repository.MemberGroupQueryDslRepository;
import codeview.main.membergroup.presentation.dto.MemberGroupDto;
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
public class MemberGroupMainController {

    private final MemberGroupQueryDslRepository memberGroupQueryDslRepository;
    private final MemberService memberService;

    private final MemberGroupService memberGroupService;

    @GetMapping
    public String index(Model model) {

        return "groups/my-groups";
    }


    @GetMapping("/list")
    public String getGroupList(Model model,
                               MemberGroupSearchCondition condition,
                               @AuthenticationPrincipal PrincipalUser principalUser,
                               @PageableDefault Pageable pageable) {

        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());
        condition.setMember(member);

        Page<MemberGroupDto> memberGroupDtos = memberGroupQueryDslRepository.searchPageComplex(condition, pageable);

        int start = (int) (Math.floor(memberGroupDtos.getTotalElements() / 10) * 10 + 1);
        int last = start + 9 < memberGroupDtos.getTotalPages() ? start + 9 : memberGroupDtos.getTotalPages();

        model.addAttribute("memberGroups", memberGroupDtos);
        model.addAttribute("start", start);
        model.addAttribute("last", last);

        log.info("memberGroupDtos.getTotalPages() = {}", ((Page<?>) memberGroupDtos).getTotalPages());
        log.info("memberGroupDtos.getTotalElements() = {}", memberGroupDtos.getTotalElements());

        return "groups/my-group-list";
    }


    @GetMapping("/admin/{id}")
    public String getGroupDetail(
            Model model, @PathVariable("id") Integer id,
            @AuthenticationPrincipal PrincipalUser principalUser) {

        MemberGroup memberGroup = memberGroupService.findById(Long.valueOf(id));

        MemberGroupDto memberGroupDto = MemberGroupDto.builder()
                .id(memberGroup.getId())
                .name(memberGroup.getName())
                .maxMember(memberGroup.getMaxMember())
                .visibility(memberGroup.getMemberGroupVisibility())
                .joinClosedTime(memberGroup.getJoinClosedTime())
                .build();

        model.addAttribute("memberGroupDto", memberGroupDto);

        log.info("memberGroupDto = {}", memberGroupDto);

        return "groups/admin-group-detail";
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

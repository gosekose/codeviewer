package codeview.main.membergroup.presentation.controller.admin.memberinfo;

import codeview.main.common.application.CsrfProviderService;
import codeview.main.groupstorage.infra.repository.GroupStorageQueryDslRepositoryImpl;
import codeview.main.groupstorage.infra.repository.query.member.MembersOfGroupCondition;
import codeview.main.groupstorage.infra.repository.query.member.MembersOfGroupPageDto;
import codeview.main.groupstorage.presentation.util.GroupStoragePage;
import codeview.main.member.application.MemberService;
import codeview.main.member.infra.repository.query.GroupMemberInfo;
import codeview.main.member.infra.repository.query.GroupMemberInfoCondition;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.solve.application.SolvesListSearchService;
import codeview.main.solve.infra.repository.query.MemberSolveInfoCondition;
import codeview.main.solve.infra.repository.query.MemberSolveInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/api/v1/groups/admin/{groupId}/members")
@RequiredArgsConstructor
public class GroupMemberInfoController {


    private final CsrfProviderService csrfProviderService;
    private final MemberService memberService;
    private final SolvesListSearchService solvesListSearchService;

    private final GroupStorageQueryDslRepositoryImpl groupStorageQueryDslRepository;
    private final GroupService groupService;

    @GetMapping
    public String getGroupMembersPage(
            @PathVariable("groupId") Integer groupId,
            @PageableDefault Pageable pageable,
            MembersOfGroupCondition condition,
            Model model) {

        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));
        condition.setMemberGroup(memberGroup);

        Page<MembersOfGroupPageDto> membersOfGroupByPage = GroupStoragePage.getMembersOfGroupByPage(groupStorageQueryDslRepository, condition, pageable, model);

        model.addAttribute("groupId", memberGroup.getId());

        return "groups/admins/my-group-members";

    }

    @GetMapping("/{memberId}")
    public String getMemberInfo(@PathVariable("groupId") Integer groupId,
                                @PathVariable("memberId") Integer memberId,
                                HttpServletRequest request,
                                GroupMemberInfoCondition groupCondition,
                                MemberSolveInfoCondition memberSolveInfoCondition,
                                Model model) {

        groupCondition.updateGroupMember(groupId, memberId);
        memberSolveInfoCondition.updateGroupMember(groupId, memberId);

        GroupMemberInfo groupMemberInfo = memberService.getGroupMemberInfo(groupCondition);
        List<MemberSolveInfoDto> memberSolveInfoNoProblemId = solvesListSearchService.getMemberSolveInfoNoProblemId(memberSolveInfoCondition);

        model.addAttribute("_csrf", csrfProviderService.createCsrf(request));
        model.addAttribute("memberInfo", groupMemberInfo);
        model.addAttribute("solves", memberSolveInfoNoProblemId);

        model.addAttribute("groupId", groupId);
        model.addAttribute("memberId", memberId);

        return "groups/admins/my-group-member-info";
    }

}

package codeview.main.membergroup.presentation.controller;

import codeview.main.common.application.CsrfProviderService;
import codeview.main.member.application.MemberService;
import codeview.main.member.infra.repository.query.GroupMemberInfo;
import codeview.main.member.infra.repository.query.GroupMemberInfoCondition;
import codeview.main.solve.application.SolvesListSearchService;
import codeview.main.solve.infra.repository.query.MemberSolveInfoCondition;
import codeview.main.solve.infra.repository.query.MemberSolveInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/api/v1/groups/admin/{groupId}/members/{memberId}")
@RequiredArgsConstructor
public class GroupMemberInfoController {


    private final CsrfProviderService csrfProviderService;
    private final MemberService memberService;
    private final SolvesListSearchService solvesListSearchService;

    @GetMapping
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

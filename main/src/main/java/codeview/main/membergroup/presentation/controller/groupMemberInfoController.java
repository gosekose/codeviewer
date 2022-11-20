package codeview.main.membergroup.presentation.controller;

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

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/api/v1/groups/admin/{groupId}/members/{memberId}")
@RequiredArgsConstructor
public class groupMemberInfoController {

    private final MemberService memberService;
    private final SolvesListSearchService solvesListSearchService;

    @GetMapping
    public String getMemberInfo(@PathVariable("groupId") String groupId,
                                @PathVariable("memberId") String memberId,
                                GroupMemberInfoCondition groupCondition,
                                MemberSolveInfoCondition solveCondition,
                                Model model) {

        groupCondition.setMemberId(Long.valueOf(memberId));
        groupCondition.setGroupId(Long.valueOf(groupId));

        solveCondition.setMemberId(Long.valueOf(memberId));
        solveCondition.setGroupId(Long.valueOf(groupId));

        List<GroupMemberInfo> groupMemberInfo = memberService.getGroupMemberInfo(groupCondition);
        List<MemberSolveInfoDto> memberSolveInfoCrossJoin = solvesListSearchService.getMemberSolveInfoCrossJoin(solveCondition);

        solveCondition.setProblemId(3L);
        List<MemberSolveInfoDto> memberSolveInfo = solvesListSearchService.getMemberSolveInfo(solveCondition);

        log.info("memberInfo.size = {}", groupMemberInfo.size());
        log.info("memberSolveInfoCrossJoin.size = {}", memberSolveInfoCrossJoin.size());
        log.info("memberSolveInfo.size = {}", memberSolveInfo.size());

        model.addAttribute("memberInfo", groupMemberInfo);
        model.addAttribute("solveInfo", memberSolveInfoCrossJoin);
        model.addAttribute("memberSolveInfo", memberSolveInfo);

        return "groups/admins/my-group-member-info";
    }

}

package codeview.main.membergroup.presentation.controller.join;

import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupJoinService;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.presentation.dao.JoinRequestDao;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/admin/{groupId}")
public class GroupJoinAdminRestController {

    private final GroupJoinService groupJoinService;
    private final MemberService memberService;
    private final GroupService groupService;

    @PostMapping("/approval")
    public String joinApprove(
            @PathVariable("groupId") Integer groupId,
            @RequestBody JoinRequestDao joinRequestDao) {

        Member member = memberService.find(Long.valueOf(joinRequestDao.getMemberId()));
        MemberGroup memberGroup = groupService.findById(Long.valueOf(joinRequestDao.getGroupId()));

        Long requestJoinId = groupJoinService.saveRequestGroupJoin(member, memberGroup);

        return null;
    }


    @PostMapping("/denial")
    public String joinDenied(
            @PathVariable("groupId") Integer groupId) {

        return null;
    }
}

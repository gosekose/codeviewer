package codeview.main.membergroup.presentation.controller.user.join;

import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupJoinService;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.GroupJoinStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static codeview.main.membergroup.domain.eumerate.GroupAutoJoin.ON;

@Slf4j
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupJoinByMemberRestController {

    private final MemberService memberService;
    private final GroupService groupService;
    private final GroupJoinService groupJoinService;

    @PostMapping("/{groupId}/join/{memberId}")
    private ResponseEntity<GroupJoinStatus> postJoinGroup(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("memberId") Integer memberId) {

        GroupJoinStatus joinStatus;

        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));
        Member member = memberService.find(Long.valueOf(Long.valueOf(memberId)));

        if (memberGroup.getGroupAutoJoin().equals(ON)) {

            joinStatus = groupJoinService.saveAutoRequestGroupJoin(member, memberGroup);


        } else {

            joinStatus = groupJoinService.requestGroupJoin(member, memberGroup);
            log.info("joinStatus = {}", joinStatus);

        }

        return new ResponseEntity<>(joinStatus, HttpStatus.TEMPORARY_REDIRECT);
    }


}

package codeview.main.membergroup.presentation.controller.admin.join;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupJoinService;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/admin/{groupId}")
public class GroupJoinAdminRestController {

    private final GroupJoinService groupJoinService;
    private final MemberService memberService;
    private final GroupService groupService;
    private final GroupStorageService groupStorageService;

    @PostMapping("/join/approval/{memberId}")
    public ResponseEntity<BaseEntity> joinApprove(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("memberId") Integer memberId) {

        Member member = memberService.find(Long.valueOf(memberId));
        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));

        groupJoinService.saveRequestGroupJoin(member, memberGroup);

        return new ResponseEntity(HttpStatus.TEMPORARY_REDIRECT);
    }


    @PostMapping("/join/denial/{memberId}")
    public ResponseEntity<BaseEntity> joinDenied(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("memberId") Integer memberId) throws IllegalAccessException {

        Member member = memberService.find(Long.valueOf(memberId));
        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));

        groupJoinService.deniedRequestGroupJoin(member, memberGroup);

        return new ResponseEntity(HttpStatus.TEMPORARY_REDIRECT);
    }


    @PostMapping("/delete/{memberId}")
    public ResponseEntity<BaseEntity> removeMember(
            @PathVariable("groupId") Integer groupId,
            @PathVariable("memberId") Integer memberId) throws IllegalAccessException {

        Member member = memberService.find(Long.valueOf(memberId));
        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));

        groupStorageService.deleteMemberAtMemberGroup(member, memberGroup);
        log.info("memberId = {}, memberGroupId = {} delete", memberId, groupId);

        groupJoinService.deniedRequestGroupJoin(member, memberGroup);

        return new ResponseEntity(HttpStatus.TEMPORARY_REDIRECT);
    }

}

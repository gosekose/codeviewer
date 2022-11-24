package codeview.main.membergroup.presentation.controller.admin.join;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupJoinService;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.repository.join.query.JoinRequestDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/admin/{groupId}")
public class GroupJoinAdminRestController {

    private final GroupJoinService groupJoinService;
    private final MemberService memberService;
    private final GroupService groupService;
    private final GroupStorageService groupStorageService;

    @PostMapping("/join/approval")
    public ResponseEntity<BaseEntity> joinApprove(
            @PathVariable("groupId") Integer groupId,
            @RequestBody JoinRequestDao joinRequestDao) {

        Member member = memberService.find(Long.valueOf(joinRequestDao.getMemberId()));
        MemberGroup memberGroup = groupService.findById(Long.valueOf(joinRequestDao.getGroupId()));

        groupJoinService.saveRequestGroupJoin(member, memberGroup);

        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/join/denial")
    public ResponseEntity<BaseEntity> joinDenied(
            @PathVariable("groupId") Integer groupId,
            @RequestBody JoinRequestDao joinRequestDao) throws IllegalAccessException {

        Member member = memberService.find(Long.valueOf(joinRequestDao.getMemberId()));
        MemberGroup memberGroup = groupService.findById(Long.valueOf(joinRequestDao.getGroupId()));

        groupJoinService.deniedRequestGroupJoin(member, memberGroup);

        return new ResponseEntity(HttpStatus.OK);
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

        return new ResponseEntity(HttpStatus.OK);
    }

}

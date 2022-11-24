package codeview.main.membergroup.presentation.controller.admin.join;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupJoinService;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.GroupJoinStatus;
import codeview.main.membergroup.infra.repository.join.query.JoinRequestDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static codeview.main.membergroup.domain.eumerate.GroupAutoJoin.ON;

@Slf4j
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupJoinByMemberRestController {

    private final MemberService memberService;
    private final GroupService groupService;
    private final GroupJoinService groupJoinService;

    @PostMapping("/{groupId}/join")
    private ResponseEntity<GroupJoinStatus> postJoinGroup(@PathVariable("groupId") Integer groupId,
                                                  @AuthenticationPrincipal PrincipalUser principalUser,
                                                  @RequestBody JoinRequestDao joinRequestDao) {

        GroupJoinStatus joinStatus;

        MemberGroup memberGroup = groupService.findById(Long.valueOf(joinRequestDao.getGroupId()));
        Member member = memberService.find(Long.valueOf(joinRequestDao.getMemberId()));

        if (memberGroup.getGroupAutoJoin().equals(ON)) {

            joinStatus = groupJoinService.saveAutoRequestGroupJoin(member, memberGroup);


        } else {

            joinStatus = groupJoinService.requestGroupJoin(member, memberGroup);
            log.info("joinStatus = {}", joinStatus);

        }

        return new ResponseEntity<>(joinStatus, HttpStatus.OK);
    }


}

package codeview.main.membergroup.presentation.controller.join;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.GroupJoinService;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.presentation.dao.JoinRequestDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupJoinByMemberRestController {

    private final MemberService memberService;
    private final GroupService groupService;
    private final GroupJoinService groupJoinService;

    @PostMapping("/{groupId}/join")
    private ResponseEntity<Boolean> postJoinGroup(@PathVariable("groupId") Integer groupId,
                                                  @AuthenticationPrincipal PrincipalUser principalUser,
                                                  @RequestBody JoinRequestDao joinRequestDao) {

        MemberGroup memberGroup = groupService.findById(Long.valueOf(joinRequestDao.getGroupId()));
        Member member = memberService.find(Long.valueOf(joinRequestDao.getMemberId()));

        boolean joinStatus = groupJoinService.requestGroupJoin(member, memberGroup);

        log.info("joinStatus = {}", joinStatus);

        return new ResponseEntity<>(joinStatus, HttpStatus.OK);

    }

    ///api/v1/groups/admin/


}

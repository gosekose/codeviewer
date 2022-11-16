package codeview.main.membergroup.application;

import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.GroupJoinRequest;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.GroupJoinStatus;
import codeview.main.membergroup.infra.repository.GroupJoinRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupJoinService {

    private final GroupStorageService groupStorageService;
    private final GroupJoinRequestRepository groupJoinRequestRepository;


    /**
     *
     * @param member
     * @param memberGroup
     *
     * member가 요청 memberGroup의 회원이 아니라면, 신청 상태로 저장 후 true
     * 만약 회원이거나 이미 요청 상태라면, 신청 상태 유지 후 false
     *
     * @return
     */
    @Transactional(readOnly = false)
    public boolean requestGroupJoin(Member member, MemberGroup memberGroup) {
        GroupStorage groupStorage = groupStorageService.findByMemberAndMemberGroup(member, memberGroup);

        if (groupStorage == null) {
            GroupJoinRequest findGroupJoinReq = groupJoinRequestRepository.findByMemberAndMemberGroup(member, memberGroup);

            if (findGroupJoinReq == null || findGroupJoinReq.getGroupJoinStatus().equals(GroupJoinStatus.NOTJOIN)) {
                groupJoinRequestRepository.save(
                        GroupJoinRequest.builder()
                                .member(member)
                                .memberGroup(memberGroup)
                                .groupJoinStatus(GroupJoinStatus.WAIT)
                                .build()
                );

                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param member
     * @param memberGroup
     *
     * 해당 그룹의 호스트가 새로원 회원의 요청을 승인할 경우, 요청 승인 수정
     *
     * @return
     */
    @Transactional
    public Long saveRequestGroupJoin(Member member, MemberGroup memberGroup) {

        Long groupStorageId = groupStorageService.save(member, memberGroup);

        // 해당 요청 request 요청 승인 변경
        GroupJoinRequest groupJoinRequest = groupJoinRequestRepository.findByMemberAndMemberGroup(member, memberGroup);
        groupJoinRequest.updateGroupStatus(GroupJoinStatus.JOIN);

        return groupStorageId;
    }

    /**
     *
     * @param member
     * @param memberGroup
     *
     *
     * 해당 그룹의 호스트가 새로운 회원의 요청을 거절한 경우, 요청 미신청 변경
     *
     *
     * @return
     */
    public Long deniedRequestGroupJoin(Member member, MemberGroup memberGroup) {

        // 해당 요청 request 요청 승인 변경
        GroupJoinRequest groupJoinRequest = groupJoinRequestRepository.findByMemberAndMemberGroup(member, memberGroup);
        groupJoinRequest.updateGroupStatus(GroupJoinStatus.NOTJOIN);

        return groupJoinRequest.getId();
    }

}

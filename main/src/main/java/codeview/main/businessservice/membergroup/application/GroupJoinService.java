package codeview.main.businessservice.membergroup.application;

import codeview.main.businessservice.groupstorage.application.GroupStorageService;
import codeview.main.businessservice.groupstorage.domain.GroupStorage;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.GroupJoinRequest;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupJoinStatus;
import codeview.main.businessservice.membergroup.infra.repository.join.GroupJoinRequestRepository;
import codeview.main.businessservice.membergroup.infra.repository.join.query.JoinRequestCondition;
import codeview.main.businessservice.membergroup.infra.repository.join.query.JoinRequestQueryPageDto;
import codeview.main.businessservice.membergroup.infra.repository.join.GroupJoinQueryDslRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupJoinService {

    private final GroupStorageService groupStorageService;
    private final GroupJoinRequestRepository groupJoinRequestRepository;

    private final GroupJoinQueryDslRepositoryImpl groupJoinQueryDslRepository;



    /**
     *
     * @param member 회원
     * @param memberGroup 멤버 그룹
     * member가 요청 memberGroup의 회원이 아니라면, 신청 상태로 저장 후 true
     * 만약 회원이거나 이미 요청 상태라면, 신청 상태 유지 후 false
     *
     * @return GroupJoinStatus
     */
    @Transactional
    public GroupJoinStatus requestGroupJoin(Member member, MemberGroup memberGroup) {
        GroupStorage groupStorage = groupStorageService.findByMemberAndMemberGroup(member, memberGroup);

        if (groupStorage == null) {

            log.info("groupStorage ");
            GroupJoinRequest findGroupJoinReq = groupJoinRequestRepository.findByMemberAndMemberGroup(member, memberGroup);

            if (findGroupJoinReq == null) {
                groupJoinRequestRepository.save(
                        GroupJoinRequest.builder()
                                .member(member)
                                .denialCount(0)
                                .memberGroup(memberGroup)
                                .groupJoinStatus(GroupJoinStatus.WAIT)
                                .build()
                );

                return GroupJoinStatus.WAIT;
            }
        }
        return GroupJoinStatus.ALREADY;
    }

    @Transactional
    public Long save(GroupJoinRequest groupJoinRequest) {
        GroupJoinRequest saveJoinRequest = groupJoinRequestRepository.save(groupJoinRequest);
        return saveJoinRequest.getId();
    }

//    @Cacheable(cacheNames = "groupJoinRequest", key="#id")
    public GroupJoinRequest findById(Long id) {
        Optional<GroupJoinRequest> optionalGroupJoinRequest = groupJoinRequestRepository.findById(id);

        return optionalGroupJoinRequest.orElseThrow(
                () -> {
                    throw new IllegalArgumentException("일치하지 않는 그룹 가입 요청");
                }
        );
    }

    public Page<JoinRequestQueryPageDto> findJoinRequestQueryPageDto(JoinRequestCondition condition, Pageable pageable) throws Exception {
        return groupJoinQueryDslRepository.findJoinRequestQueryPageDto(condition, pageable);
    }


    /**
     *
     * @param member 회원
     * @param memberGroup 멤버 그룹
     *
     * 해당 그룹의 호스트가 새로원 회원의 요청을 승인할 경우, 요청 승인 수정
     *
     * @return 저장 후 storageId
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
     * 자동 가입 auto 설정
     *
     * @param member 회원
     * @param memberGroup 가입 그룹
     * @return 그룹 가입 상태
     */
    @Transactional
    public GroupJoinStatus saveAutoRequestGroupJoin(Member member, MemberGroup memberGroup) {

        GroupJoinRequest findGroupJoinReq = groupJoinRequestRepository.findByMemberAndMemberGroup(member, memberGroup);

        if (findGroupJoinReq == null) {

            groupJoinRequestRepository.save(
                    GroupJoinRequest.builder()
                            .member(member)
                            .denialCount(0)
                            .memberGroup(memberGroup)
                            .groupJoinStatus(GroupJoinStatus.JOIN)
                            .build());

            groupStorageService.save(member, memberGroup);

        } else {

            if (findGroupJoinReq.getGroupJoinStatus().equals(GroupJoinStatus.NOTJOIN)) {
                return GroupJoinStatus.NOTJOIN;
            }
            if (findGroupJoinReq.getGroupJoinStatus().equals(GroupJoinStatus.ONEDELETE)) {
                groupStorageService.save(member, memberGroup);
                findGroupJoinReq.updateGroupStatus(GroupJoinStatus.JOIN);
            }
        }

        return GroupJoinStatus.JOIN;
    }



    /**
     *
     * @param member 회원
     * @param memberGroup 멤버 그룹
     * 해당 그룹의 호스트가 새로운 회원의 요청을 거절한 경우, 요청 미신청 변경
     * @return
     */
    @Transactional
    public Long deniedRequestGroupJoin(Member member, MemberGroup memberGroup) throws IllegalAccessException {

        // 해당 요청 request 요청 승인 변경
        GroupJoinRequest groupJoinRequest = groupJoinRequestRepository.findByMemberAndMemberGroup(member, memberGroup);

        if (groupJoinRequest == null) {
            throw new IllegalAccessException("허용되지 않는 접근입니다.");
        }

        if (groupJoinRequest.getDenialCount() < 2) {
            groupJoinRequest.updateGroupStatus(GroupJoinStatus.ONEDELETE);

        } else {
            groupJoinRequest.updateGroupStatus(GroupJoinStatus.NOTJOIN);
        }

        return groupJoinRequest.getId();
    }


}

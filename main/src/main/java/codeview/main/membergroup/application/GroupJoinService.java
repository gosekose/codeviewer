package codeview.main.membergroup.application;

import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.GroupJoinRequest;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.GroupJoinStatus;
import codeview.main.membergroup.infra.repository.join.GroupJoinQueryDslRepositoryImpl;
import codeview.main.membergroup.infra.repository.join.GroupJoinRequestRepository;
import codeview.main.membergroup.infra.repository.join.query.JoinRequestCondition;
import codeview.main.membergroup.infra.repository.join.query.JoinRequestQueryPageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static codeview.main.membergroup.domain.eumerate.GroupJoinStatus.*;

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
            GroupJoinRequest findGroupJoinReq = groupJoinRequestRepository.findByMemberAndMemberGroup(member, memberGroup);

            if (findGroupJoinReq == null) {
                groupJoinRequestRepository.save(
                        GroupJoinRequest.builder()
                                .member(member)
                                .denialCount(0)
                                .memberGroup(memberGroup)
                                .groupJoinStatus(WAIT)
                                .build()
                );

                return WAIT;
            }
        }
        return ALREADY;
    }

    @Transactional
    public Long save(GroupJoinRequest groupJoinRequest) {
        GroupJoinRequest saveJoinRequest = groupJoinRequestRepository.save(groupJoinRequest);
        return saveJoinRequest.getId();
    }

    @Cacheable(cacheNames = "groupJoinRequest", key="#id")
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
        groupJoinRequest.updateGroupStatus(JOIN);

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

        if (findGroupJoinReq != null) {

            if (findGroupJoinReq.getGroupJoinStatus().equals(ONEDELETE)) {
                findGroupJoinReq.updateGroupStatus(JOIN);
            } else if (findGroupJoinReq.getGroupJoinStatus().equals(NOTJOIN)) {
                return NOTJOIN;
            }
        } else {

            // 해당 요청 request 요청 승인 저장
            groupJoinRequestRepository.save(
                    GroupJoinRequest.builder()
                            .member(member)
                            .denialCount(0)
                            .memberGroup(memberGroup)
                            .groupJoinStatus(JOIN)
                            .build());
        }

        groupStorageService.save(member, memberGroup);

        return JOIN;
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
            groupJoinRequest.updateGroupStatus(ONEDELETE);

        } else {
            groupJoinRequest.updateGroupStatus(NOTJOIN);
        }

        return groupJoinRequest.getId();
    }


}

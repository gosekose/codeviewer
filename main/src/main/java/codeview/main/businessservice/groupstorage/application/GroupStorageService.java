package codeview.main.businessservice.groupstorage.application;

import codeview.main.businessservice.groupstorage.domain.GroupStorage;
import codeview.main.businessservice.groupstorage.infra.repository.GroupStorageRepository;
import codeview.main.businessservice.groupstorage.infra.repository.query.list.GroupStorageListCondition;
import codeview.main.businessservice.groupstorage.infra.repository.query.list.GroupStorageListDto;
import codeview.main.businessservice.groupstorage.infra.repository.GroupStorageQueryDslRepositoryImpl;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupAuthority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupStorageService {

    private final GroupStorageRepository groupStorageRepository;
    private final GroupStorageQueryDslRepositoryImpl groupStorageQueryDslRepositoryImpl;

    public GroupStorage findByMemberAndMemberGroup(Member member, MemberGroup memberGroup) {
        return groupStorageRepository.findByMemberAndMemberGroup(member, memberGroup);
    }

    public Page<GroupStorageListDto> getGroupStorageListForMember(GroupStorageListCondition condition, Pageable pageable) {
        return groupStorageQueryDslRepositoryImpl.searchGroupListComplex(condition, pageable);
    }

    @Transactional
    public Long save(Member member, MemberGroup memberGroup) {
        GroupStorage saveGroupStorage = groupStorageRepository.save(
                GroupStorage.builder()
                        .member(member)
                        .memberGroup(memberGroup)
                        .memberGroupAuthority(MemberGroupAuthority.USER)
                        .build()
        );

        return saveGroupStorage.getId();

    }

    @Transactional
    public void deleteMemberAtMemberGroup(Member member, MemberGroup memberGroup) {
        groupStorageRepository.deleteByMemberAndMemberGroupJpql(member, memberGroup);
    }

}

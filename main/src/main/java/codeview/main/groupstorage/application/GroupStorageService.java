package codeview.main.groupstorage.application;

import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.groupstorage.infra.repository.GroupStorageQueryDslRepositoryImpl;
import codeview.main.groupstorage.infra.repository.GroupStorageRepository;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.MemberGroupAuthority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}

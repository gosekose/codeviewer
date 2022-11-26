package codeview.main.membergroup.application;

import codeview.main.membergroup.infra.repository.membergroup.MemberGroupQueryDslRepositoryImpl;
import codeview.main.membergroup.infra.repository.membergroup.query.MemberGroupSearchCondition;
import codeview.main.membergroup.presentation.dto.GroupForPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupsGetMemberPageService {

    private final MemberGroupQueryDslRepositoryImpl memberGroupQueryDslRepository;

//    @Cacheable(cacheNames = "myGroupSearch", key = "#condition.member.id + #pageable.pageNumber")
    public Page<GroupForPageDto> getMyMemberGroupsPage(MemberGroupSearchCondition condition, Pageable pageable) {
        return memberGroupQueryDslRepository.searchPageComplex(condition, pageable);
    }

//    @Cacheable(cacheNames = "groupSearch", key = "#pageable.pageNumber")
    public Page<GroupForPageDto> getMemberGroupsPage(MemberGroupSearchCondition condition, Pageable pageable) {
        return memberGroupQueryDslRepository.searchPageComplex(condition, pageable);
    }

    public Page<GroupForPageDto> getSearchGroupByJoinStatus(MemberGroupSearchCondition condition, Pageable pageable) {
        return memberGroupQueryDslRepository.searchGroupByJoinStatus(condition, pageable);
    }

}

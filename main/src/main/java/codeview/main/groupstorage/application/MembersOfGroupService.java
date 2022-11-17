package codeview.main.groupstorage.application;

import codeview.main.groupstorage.infra.repository.GroupStorageQueryDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MembersOfGroupService {

    private final GroupStorageQueryDslRepository groupStorageQueryDslRepository;

    private final GroupStorageService groupStorageService;

//    public Page<MembersOfGroupPageDto> getMembersOfGroup(MembersOfGroupCondition condition, Pageable pageable) {
//        return groupStorageQueryDslRepository.searchPageComplex(condition, pageable);
//    }
}

package codeview.main.groupstorage.infra.repository;

import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.groupstorage.infra.repository.query.MembersOfGroupCondition;
import codeview.main.groupstorage.presentation.dto.MembersOfGroupPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupStorageQueryDslRepository {
    List<GroupStorage> search(MembersOfGroupCondition membersOfGroupCondition);

    Page<MembersOfGroupPageDto> searchPageComplex(MembersOfGroupCondition membersOfGroupCondition, Pageable pageable);

}

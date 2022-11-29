package codeview.main.businessservice.groupstorage.infra.repository;

import codeview.main.businessservice.groupstorage.domain.GroupStorage;
import codeview.main.businessservice.groupstorage.infra.repository.query.list.GroupStorageListCondition;
import codeview.main.businessservice.groupstorage.infra.repository.query.list.GroupStorageListDto;
import codeview.main.businessservice.groupstorage.infra.repository.query.member.MembersOfGroupCondition;
import codeview.main.businessservice.groupstorage.infra.repository.query.member.MembersOfGroupPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupStorageQueryDslRepository {
    List<GroupStorage> search(MembersOfGroupCondition membersOfGroupCondition);

    Page<MembersOfGroupPageDto> searchMemberOfGroupComplex(MembersOfGroupCondition membersOfGroupCondition, Pageable pageable);

    Page<GroupStorageListDto> searchGroupListComplex(GroupStorageListCondition condition, Pageable pageable);

}

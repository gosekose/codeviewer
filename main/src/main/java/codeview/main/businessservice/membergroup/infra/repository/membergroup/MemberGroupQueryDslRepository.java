package codeview.main.businessservice.membergroup.infra.repository.membergroup;

import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.infra.repository.membergroup.query.MemberGroupSearchCondition;
import codeview.main.businessservice.membergroup.presentation.dto.GroupForPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberGroupQueryDslRepository {

    List<MemberGroup> search(MemberGroupSearchCondition memberGroupSearchCondition);

    Page<GroupForPageDto> searchPageComplex(MemberGroupSearchCondition condition, Pageable pageable);

    Page<GroupForPageDto> searchGroupByJoinStatus(MemberGroupSearchCondition condition, Pageable pageable);


}

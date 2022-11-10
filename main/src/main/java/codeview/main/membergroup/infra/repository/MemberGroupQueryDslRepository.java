package codeview.main.membergroup.infra.repository;

import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.infra.dao.MemberGroupSearchCondition;
import codeview.main.membergroup.presentation.dto.MemberGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberGroupQueryDslRepository {

    List<MemberGroup> search(MemberGroupSearchCondition memberGroupSearchCondition);

    Page<MemberGroupDto> searchPageComplex(MemberGroupSearchCondition condition, Pageable pageable);


}

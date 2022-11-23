package codeview.main.membergroup.infra.repository;

import codeview.main.membergroup.infra.repository.query.dto.JoinRequestCondition;
import codeview.main.membergroup.infra.repository.query.dto.JoinRequestQueryPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJoinQueryDslRepository {

    Page<JoinRequestQueryPageDto> findJoinRequestQueryPageDto(JoinRequestCondition condition, Pageable pageable) throws Exception;
}

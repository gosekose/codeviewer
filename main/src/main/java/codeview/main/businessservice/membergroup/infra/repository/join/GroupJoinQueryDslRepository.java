package codeview.main.businessservice.membergroup.infra.repository.join;

import codeview.main.businessservice.membergroup.infra.repository.join.query.JoinRequestCondition;
import codeview.main.businessservice.membergroup.infra.repository.join.query.JoinRequestQueryPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJoinQueryDslRepository {

    Page<JoinRequestQueryPageDto> findJoinRequestQueryPageDto(JoinRequestCondition condition, Pageable pageable) throws Exception;
}

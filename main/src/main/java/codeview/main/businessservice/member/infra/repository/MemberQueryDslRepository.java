package codeview.main.businessservice.member.infra.repository;

import codeview.main.businessservice.member.infra.repository.query.GroupMemberInfo;
import codeview.main.businessservice.member.infra.repository.query.GroupMemberInfoCondition;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberQueryDslRepository {

    GroupMemberInfo searchMemberInfoUsingGroup(GroupMemberInfoCondition condition);

}

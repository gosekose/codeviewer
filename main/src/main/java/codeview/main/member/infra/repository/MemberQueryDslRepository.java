package codeview.main.member.infra.repository;

import codeview.main.member.infra.repository.query.GroupMemberInfo;
import codeview.main.member.infra.repository.query.GroupMemberInfoCondition;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberQueryDslRepository {

    GroupMemberInfo searchMemberInfoUsingGroup(GroupMemberInfoCondition condition);

}

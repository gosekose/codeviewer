package codeview.main.member.infra.repository;

import codeview.main.member.infra.repository.query.GroupMemberInfo;
import codeview.main.member.infra.repository.query.GroupMemberInfoCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberQueryDslRepository {

    List<GroupMemberInfo> searchMemberInfoUsingGroup(GroupMemberInfoCondition condition);

}

package codeview.main.businessservice.member.infra.repository;

import codeview.main.businessservice.member.infra.repository.query.GroupMemberInfo;
import codeview.main.businessservice.member.infra.repository.query.GroupMemberInfoCondition;
import codeview.main.businessservice.member.infra.repository.query.MemberCondition;
import codeview.main.businessservice.member.infra.repository.query.MemberProfileDto;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberQueryDslRepository {

    GroupMemberInfo searchMemberInfoUsingGroup(GroupMemberInfoCondition condition);

    MemberProfileDto getMemberProfile(MemberCondition condition);
}

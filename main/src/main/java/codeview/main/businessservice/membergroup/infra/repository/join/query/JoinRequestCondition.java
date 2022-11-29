package codeview.main.businessservice.membergroup.infra.repository.join.query;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupJoinStatus;
import lombok.Data;

@Data
public class JoinRequestCondition {

    private Member member;
    private GroupJoinStatus groupJoinStatus = GroupJoinStatus.WAIT;
}

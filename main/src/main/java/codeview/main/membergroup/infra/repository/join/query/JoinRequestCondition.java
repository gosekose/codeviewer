package codeview.main.membergroup.infra.repository.join.query;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.eumerate.GroupJoinStatus;
import lombok.Data;

@Data
public class JoinRequestCondition {

    private Member member;
    private GroupJoinStatus groupJoinStatus = GroupJoinStatus.WAIT;
}
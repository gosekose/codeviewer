package codeview.main.member.infra.repository.query;

import lombok.Data;

@Data
public class GroupMemberInfoCondition {

    private Long memberId;
    private Long groupId;

}

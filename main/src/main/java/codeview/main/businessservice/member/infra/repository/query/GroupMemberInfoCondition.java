package codeview.main.businessservice.member.infra.repository.query;

import lombok.Data;

@Data
public class GroupMemberInfoCondition {

    private Long memberId;
    private Long groupId;

    public void updateGroupMember(Integer groupId, Integer memberId) {
        this.groupId = Long.valueOf(groupId);
        this.memberId = Long.valueOf(memberId);
    }

    public void updateMember(Long memberId) {
        this.memberId = memberId;
    }

}

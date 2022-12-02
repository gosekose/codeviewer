package codeview.main.businessservice.member.infra.repository.query;

import lombok.Data;

@Data
public class MemberCondition {
    private Long memberId;

    public void updateMember(Long memberId) {
        this.memberId = memberId;
    }
}

package codeview.main.businessservice.membergroup.infra.repository.join.query;

import lombok.Data;

@Data
public class JoinRequestDao {
    private String groupId;
    private String memberId;

    public JoinRequestDao(){}
}

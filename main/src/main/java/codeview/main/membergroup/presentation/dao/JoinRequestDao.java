package codeview.main.membergroup.presentation.dao;

import lombok.Data;

@Data
public class JoinRequestDao {
    private String groupId;
    private String memberId;

    public JoinRequestDao(){}
}

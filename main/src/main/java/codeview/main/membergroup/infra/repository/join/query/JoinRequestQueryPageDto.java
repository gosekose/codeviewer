package codeview.main.membergroup.infra.repository.join.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class JoinRequestQueryPageDto {

    private Long groupId;
    private String groupName;

    private Long userId;
    private String userName;
    private String schoolName;
    private String department;
    private String privateIdInSchool;

    @Builder
    @QueryProjection
    public JoinRequestQueryPageDto(Long groupId, String groupName, Long userId, String userName, String schoolName, String department, String privateIdInSchool) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.userId = userId;
        this.userName = userName;
        this.schoolName = schoolName;
        this.department = department;
        this.privateIdInSchool = privateIdInSchool;
    }
}

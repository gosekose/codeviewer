package codeview.main.businessservice.groupstorage.infra.repository.query.list;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class GroupStorageListDto {

    private Long groupId;
    private String groupName;
    private String creatorName;
    private String creatorSchoolName;
    private String creatorDepartment;

    @Builder
    @QueryProjection
    public GroupStorageListDto(Long groupId, String groupName, String creatorName, String creatorSchoolName, String creatorDepartment) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.creatorName = creatorName;
        this.creatorSchoolName = creatorSchoolName;
        this.creatorDepartment = creatorDepartment;
    }
}

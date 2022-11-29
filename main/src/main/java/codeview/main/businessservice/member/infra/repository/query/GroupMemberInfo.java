package codeview.main.businessservice.member.infra.repository.query;

import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupAuthority;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupMemberInfo {

    private String memberName;

    private String memberImage;
    private String schoolName;
    private String department;
    private String privateIdInSchool;
    private MemberGroupAuthority memberGroupAuthority;
    private LocalDateTime joinTime;

    @Builder
    @QueryProjection
    public GroupMemberInfo(String memberName, String memberImage, String schoolName, String department, String privateIdInSchool, MemberGroupAuthority memberGroupAuthority, LocalDateTime joinTime) {
        this.memberName = memberName;
        this.memberImage = memberImage;
        this.schoolName = schoolName;
        this.department = department;
        this.privateIdInSchool = privateIdInSchool;
        this.memberGroupAuthority = memberGroupAuthority;
        this.joinTime = joinTime;
    }
}

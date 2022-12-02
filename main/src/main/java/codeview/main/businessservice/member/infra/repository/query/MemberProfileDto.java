package codeview.main.businessservice.member.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberProfileDto {

    private String userComment;
    private String memberName;
    private String schoolName;
    private String department;
    private String privateIdInSchool;
    private String work;
    private String registrationId;
    private String email;

    private String facebookEmail;
    private String linkedInEmail;
    private String githubEmail;

    @QueryProjection
    @Builder
    public MemberProfileDto(String userComment, String memberName, String schoolName, String department, String privateIdInSchool,
                            String work, String registrationId, String email, String facebookEmail, String linkedInEmail, String githubEmail) {
        this.userComment = userComment;
        this.memberName = memberName;
        this.schoolName = schoolName;
        this.department = department;
        this.privateIdInSchool = privateIdInSchool;
        this.work = work;
        this.registrationId = registrationId;
        this.email = email;
        this.facebookEmail = facebookEmail;
        this.linkedInEmail = linkedInEmail;
        this.githubEmail = githubEmail;
    }
}

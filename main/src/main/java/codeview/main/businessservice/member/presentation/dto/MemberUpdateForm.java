package codeview.main.businessservice.member.presentation.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class MemberUpdateForm {

    private Long id;
    private String memberName;
    private Integer age;
    private Integer school;

    private String picture;

    private String department;

    private String privateIdInSchool;

    private String work;

    private String userComment;
    private String facebookEmail;
    private String linkedInEmail;
    private String githubEmail;


    @Builder
    public MemberUpdateForm(Long id, String memberName, Integer age, Integer school, String picture, String department, String privateIdInSchool,
                            String work, String userComment, String facebookEmail, String linkedInEmail, String githubEmail) {
        this.id = id;
        this.memberName = memberName;
        this.age = age;
        this.school = school;
        this.picture = picture;
        this.department = department;
        this.privateIdInSchool = privateIdInSchool;
        this.work = work;
        this.userComment = userComment;
        this.facebookEmail = facebookEmail;
        this.linkedInEmail = linkedInEmail;
        this.githubEmail = githubEmail;
    }
}

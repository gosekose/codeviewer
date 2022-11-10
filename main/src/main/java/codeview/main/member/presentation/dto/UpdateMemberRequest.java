package codeview.main.member.presentation.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class UpdateMemberRequest {

    private Long id;
    private String memberName;
    private Integer age;
    private String zipcode;
    private String details;
    private Integer school;
    private String work;

    @Builder
    public UpdateMemberRequest(Long id, String memberName, Integer age, String zipcode, String details, Integer school, String work) {
        this.id = id;
        this.memberName = memberName;
        this.age = age;
        this.zipcode = zipcode;
        this.details = details;
        this.school = school;
        this.work = work;
    }
}

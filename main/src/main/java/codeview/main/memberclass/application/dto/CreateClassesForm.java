package codeview.main.memberclass.application.dto;

import codeview.main.memberclass.domain.MemberClassesVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor @Setter
@AllArgsConstructor
public class CreateClassesForm {

    private String name;
    private Integer maxMember;

    private MemberClassesVisibility memberClassesVisibility;

    private LocalDateTime joinClosedTime;

    private String description;

    private String skillTag;

    private String password;
}

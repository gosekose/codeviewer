package codeview.main.memberclass.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor
@AllArgsConstructor
public class CreateClassesForm {

    private String className;
    private Integer maxMember;

    private boolean visibilityClass;

    private LocalDateTime joinClosedTime;

    private String description;

    private String skillTag;

    private String password;
}

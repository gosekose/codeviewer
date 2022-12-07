package codeview.main.businessservice.membergroup.presentation.dto;

import codeview.main.businessservice.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @NoArgsConstructor @Setter
@AllArgsConstructor
public class CreateGroupForm {

    @NotBlank(message = "그룹명은 필수입니다.")
    @Length(min = 4, max = 100, message = "그룹명의 길이는 4 ~ 100 사이입니다.")
    private String name;

    @Range(min = 10, max = 1000000)
    private Integer maxMember;

    @NotNull(message = "공개 여부는 필수입니다.")
    private MemberGroupVisibility memberGroupVisibility;

    @NotNull(message = "자동 가입 설정 여부는 필수입니다.")
    private GroupAutoJoin groupAutoJoin;

    @Future(message = "현재 시간 이후만 체크 가능합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime joinClosedTime;

    @Length(max = 1000, message = "길이는 최대 1000자 입니다.")
    private String description;

    @Length(max = 100)
    private String skillTag;

    @Length(max = 100)
    private String password;

}

package codeview.main.businessservice.membergroup.presentation.dto;

import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupListResponseDto {

    private Long id;
    private String name;
    private MemberGroupVisibility memberGroupVisibility;
    private Integer maxMember;
    private Integer countMember;
    private LocalDateTime joinClosedTime;


}

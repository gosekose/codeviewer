package codeview.main.membergroup.presentation.dto;

import codeview.main.membergroup.domain.MemberGroupVisibility;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MemberGroupDto {

    private String name;
    private MemberGroupVisibility visibility;

    private Integer maxMember;

    private LocalDateTime joinClosedTime;

    @QueryProjection
    public MemberGroupDto(String name, MemberGroupVisibility visibility, Integer maxMember, LocalDateTime joinClosedTime) {
        this.name = name;
        this.visibility = visibility;
        this.maxMember = maxMember;
        this.joinClosedTime = joinClosedTime;
    }
}

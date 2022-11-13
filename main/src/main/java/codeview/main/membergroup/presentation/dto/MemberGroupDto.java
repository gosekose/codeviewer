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

    private Long id;

    private String name;
    private MemberGroupVisibility visibility;

    private Integer maxMember;

    private LocalDateTime joinClosedTime;

    @QueryProjection
    public MemberGroupDto(Long id, String name, MemberGroupVisibility visibility, Integer maxMember, LocalDateTime joinClosedTime) {
        this.id = id;
        this.name = name;
        this.visibility = visibility;
        this.maxMember = maxMember;
        this.joinClosedTime = joinClosedTime;
    }
}

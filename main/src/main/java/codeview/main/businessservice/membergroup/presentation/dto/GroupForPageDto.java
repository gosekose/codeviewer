package codeview.main.businessservice.membergroup.presentation.dto;

import codeview.main.businessservice.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GroupForPageDto {

    private Long id;

    private String name;
    private MemberGroupVisibility visibility;

    private Integer maxMember;

    private LocalDateTime joinClosedTime;

    private GroupAutoJoin groupAutoJoin;

    @Builder
    @QueryProjection
    public GroupForPageDto(Long id, String name, MemberGroupVisibility visibility, Integer maxMember, LocalDateTime joinClosedTime, GroupAutoJoin groupAutoJoin) {
        this.id = id;
        this.name = name;
        this.visibility = visibility;
        this.maxMember = maxMember;
        this.joinClosedTime = joinClosedTime;
        this.groupAutoJoin = groupAutoJoin;
    }


}

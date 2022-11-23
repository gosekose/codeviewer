package codeview.main.groupstorage.infra.repository.query.member;

import codeview.main.membergroup.domain.eumerate.MemberGroupAuthority;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class MembersOfGroupPageDto {

    private Long id;
    private String name;
    private String department;
    private String privateIdInSchool;
    private MemberGroupAuthority memberGroupAuthority;
    private LocalDateTime joinTime;


    @Builder
    @QueryProjection
    public MembersOfGroupPageDto(Long id, String name, String department, String privateIdInSchool, MemberGroupAuthority memberGroupAuthority, LocalDateTime joinTime) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.privateIdInSchool = privateIdInSchool;
        this.memberGroupAuthority = memberGroupAuthority;
        this.joinTime = joinTime;
    }




}

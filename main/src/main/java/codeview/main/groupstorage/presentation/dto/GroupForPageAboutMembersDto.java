package codeview.main.groupstorage.presentation.dto;

import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.membergroup.domain.eumerate.MemberGroupAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupForPageAboutMembersDto {

    private String name;
    private String department;
    private String privateIdInSchool;
    private MemberGroupAuthority memberGroupAuthority;
    private LocalDateTime joinTime;


    public GroupForPageAboutMembersDto(GroupStorage groupStorage) {
        this.name = groupStorage.getMember().getMemberName();
        this.department = groupStorage.getMember().getDepartment();
        this.privateIdInSchool = groupStorage.getMember().getPrivateIdInSchool();
        this.memberGroupAuthority = groupStorage.getMemberGroupAuthority();
        this.joinTime = groupStorage.getCreatedAt();
    }

}

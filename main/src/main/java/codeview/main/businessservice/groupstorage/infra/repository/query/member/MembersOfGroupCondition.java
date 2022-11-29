package codeview.main.businessservice.groupstorage.infra.repository.query.member;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MembersOfGroupCondition {

    private Member member;
    private MemberGroup memberGroup;
    private MemberGroupAuthority memberGroupAuthority;

}

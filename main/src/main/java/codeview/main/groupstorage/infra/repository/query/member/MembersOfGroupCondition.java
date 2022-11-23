package codeview.main.groupstorage.infra.repository.query.member;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.eumerate.MemberGroupAuthority;
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

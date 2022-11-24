package codeview.main.membergroup.infra.repository.membergroup.query;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.membergroup.domain.eumerate.MemberGroupVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberGroupSearchCondition {

    private Member creator;
    private Member admin;
    private String name;
    private MemberGroupVisibility visibility;
    private GroupAutoJoin groupAutoJoin;

}

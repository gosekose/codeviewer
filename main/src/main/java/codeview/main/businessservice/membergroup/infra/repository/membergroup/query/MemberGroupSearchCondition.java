package codeview.main.businessservice.membergroup.infra.repository.membergroup.query;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.eumerate.GroupAutoJoin;
import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
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

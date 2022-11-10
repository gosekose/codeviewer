package codeview.main.membergroup.infra.dao;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroupVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberGroupSearchCondition {

    private Member member;
    private String name;
    private MemberGroupVisibility visibility;

}

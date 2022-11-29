package codeview.main.businessservice.board.infra.repository.query;

import codeview.main.businessservice.board.domain.enumtype.Nondisclosure;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.domain.Problem;
import lombok.Data;

@Data
public class BoardListCondition {

    private MemberGroup memberGroup;
    private Problem problem;
    private Member member;
    private Nondisclosure nondisclosure;

}

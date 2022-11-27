package codeview.main.board.infra.repository.query;

import codeview.main.board.domain.enumtype.Nondisclosure;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.domain.Problem;
import lombok.Data;

@Data
public class BoardListCondition {

    private MemberGroup memberGroup;
    private Problem problem;
    private Member member;
    private Nondisclosure nondisclosure;

}

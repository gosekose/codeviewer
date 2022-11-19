package codeview.main.problem.infra.repository.query;

import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import lombok.Data;

@Data
public class ProblemDetailPageCond {

    private Member creator;
    private MemberGroup memberGroup;
    private String name;
    private Member myMember;

}

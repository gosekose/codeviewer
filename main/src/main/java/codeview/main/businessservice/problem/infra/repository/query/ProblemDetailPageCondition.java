package codeview.main.businessservice.problem.infra.repository.query;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import lombok.Data;

@Data
public class ProblemDetailPageCondition {

    private Member creator;
    private MemberGroup memberGroup;
    private String name;
    private Member myMember;

}

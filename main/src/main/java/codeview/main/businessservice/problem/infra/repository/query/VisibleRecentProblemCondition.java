package codeview.main.businessservice.problem.infra.repository.query;

import codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility;
import lombok.Data;

import static codeview.main.businessservice.membergroup.domain.eumerate.MemberGroupVisibility.VISIBLE;

@Data
public class VisibleRecentProblemCondition {

    private MemberGroupVisibility memberGroupVisibility = VISIBLE;
    private Long memberId;
}

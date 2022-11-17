package codeview.main.problem.infra.repository.query;

import codeview.main.membergroup.domain.MemberGroup;
import lombok.Data;

@Data
public class ProblemListSearchCondition {

    private MemberGroup memberGroup;
    private String name;

}

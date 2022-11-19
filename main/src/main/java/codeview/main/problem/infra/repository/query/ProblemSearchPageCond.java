package codeview.main.problem.infra.repository.query;

import lombok.Data;

@Data
public class ProblemSearchPageCond {

    private Long memberGroupId;
    private String creatorName;
    private String groupName;
    private String problemName;
    private Long memberId;

}

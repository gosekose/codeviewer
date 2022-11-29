package codeview.main.businessservice.solve.infra.repository.query;

import lombok.Data;

@Data
public class SolvesOfProblemCondition {

    private Long problemId;
    private Long memberId;
    private Integer loeScore;
    private Integer goeScore;

}

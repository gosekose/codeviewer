package codeview.main.solve.infra.repository.query;

import lombok.Data;

@Data
public class MemberSolveInfoCondition {

    private Long groupId;
    private Long memberId;
    private Long problemId;
}

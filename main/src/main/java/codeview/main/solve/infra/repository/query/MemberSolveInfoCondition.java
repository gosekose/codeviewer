package codeview.main.solve.infra.repository.query;

import lombok.Data;

@Data
public class MemberSolveInfoCondition {

    private Long groupId;
    private Long memberId;
    private Long problemId;
    private int solveCount;

    public void updateGroupMember(Integer groupId, Integer memberId) {
        this.groupId = Long.valueOf(groupId);
        this.memberId = Long.valueOf(memberId);
    }

    public void updateMemberProblem(Integer memberId, Integer problemId) {
        this.memberId = Long.valueOf(memberId);
        this.problemId = Long.valueOf(problemId);
    }

    public void updateSolveCount(int solveCount) {
        this.solveCount = solveCount;
    }


}

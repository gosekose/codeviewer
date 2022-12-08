package codeview.main.businessservice.problem.infra.repository.query;

import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import codeview.main.businessservice.solve.domain.enumtype.SolveStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class VisibleRecentProblemDto {

    private Long problemId;
    private String memberGroupName;
    private String problemName;
    private ProblemDifficulty problemDifficulty;
    private SolveStatus solveStatus;

    @QueryProjection
    @Builder
    public VisibleRecentProblemDto(Long problemId, String memberGroupName, String problemName, ProblemDifficulty problemDifficulty, SolveStatus solveStatus) {
        this.problemId = problemId;
        this.memberGroupName = memberGroupName;
        this.problemName = problemName;
        this.problemDifficulty = problemDifficulty;
        this.solveStatus = solveStatus;
    }

}

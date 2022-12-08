package codeview.main.businessservice.problem.infra.repository.query;

import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class VisibleRecentProblemNoLoginDto {

    private Long problemId;
    private String memberGroupName;
    private String problemName;
    private ProblemDifficulty problemDifficulty;

    @QueryProjection
    @Builder
    public VisibleRecentProblemNoLoginDto(Long problemId, String memberGroupName, String problemName, ProblemDifficulty problemDifficulty) {
        this.problemId = problemId;
        this.memberGroupName = memberGroupName;
        this.problemName = problemName;
        this.problemDifficulty = problemDifficulty;
    }

}

package codeview.main.problem.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class ProblemSearchPageDto {

    private Long problemId;
    private String problemName;
    private String groupName;
    private String groupCreatorName;



    @Builder
    @QueryProjection
    public ProblemSearchPageDto(Long problemId, String problemName, String groupName, String groupCreatorName) {
        this.problemId = problemId;
        this.problemName = problemName;
        this.groupName = groupName;
        this.groupCreatorName = groupCreatorName;
    }
}

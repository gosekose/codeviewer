package codeview.main.problem.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class ProblemSearchForBoardDto {

    private Long problemId;
    private String name;

    @Builder
    @QueryProjection
    public ProblemSearchForBoardDto(Long problemId, String name) {
        this.problemId = problemId;
        this.name = name;
    }
}

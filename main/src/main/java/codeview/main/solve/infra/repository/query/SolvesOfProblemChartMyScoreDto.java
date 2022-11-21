package codeview.main.solve.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class SolvesOfProblemChartMyScoreDto {

    private Long solveId;
    private Integer number;
    private Integer myScore;

    @Builder
    @QueryProjection
    public SolvesOfProblemChartMyScoreDto(Long solveId, Integer number, Integer myScore) {
        this.solveId = solveId;
        this.number = number;
        this.myScore = myScore;
    }
}

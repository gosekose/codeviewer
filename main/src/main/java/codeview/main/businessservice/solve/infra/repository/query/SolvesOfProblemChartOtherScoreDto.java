package codeview.main.businessservice.solve.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class SolvesOfProblemChartOtherScoreDto {

    private Integer number;
    private double otherScore;

    @Builder
    @QueryProjection
    public SolvesOfProblemChartOtherScoreDto(Integer number, double otherScore) {
        this.number = number;
        this.otherScore = otherScore;
    }
}

package codeview.main.businessservice.solve.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SolvesOfProblemDto {

    private Long solveId;
    private Long problemId;
    private String problemName;
    private Long memberId;
    private String memberName;
    private LocalDateTime createdAt;
    private int score;

    @Builder
    @QueryProjection
    public SolvesOfProblemDto(Long solveId, Long problemId, String problemName, Long memberId,
                              String memberName, LocalDateTime createdAt, int score) {
        this.solveId = solveId;
        this.problemId = problemId;
        this.problemName = problemName;
        this.memberId = memberId;
        this.memberName = memberName;
        this.createdAt = createdAt;
        this.score = score;
    }
}

package codeview.main.solve.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberSolveInfoDto {

    private Long solveId;
    private Long problemId;
    private String problemName;
    private Integer score;
    private LocalDateTime createdAt;


    @Builder
    @QueryProjection

    public MemberSolveInfoDto(Long solveId, Long problemId, String problemName, Integer score, LocalDateTime createdAt) {
        this.solveId = solveId;
        this.problemId = problemId;
        this.problemName = problemName;
        this.score = score;
        this.createdAt = createdAt;
    }
}

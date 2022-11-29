package codeview.main.businessservice.solve.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberSolveInfoDto {

    private Long problemId;
    private Long triedSolveCount;
    private String problemName;
    private Integer maxScore;
    private LocalDateTime lastModifiedTime;


    @Builder
    @QueryProjection
    public MemberSolveInfoDto(Long problemId, Long triedSolveCount, String problemName, Integer maxScore, LocalDateTime lastModifiedTime) {
        this.problemId = problemId;
        this.triedSolveCount = triedSolveCount;
        this.problemName = problemName;
        this.maxScore = maxScore;
        this.lastModifiedTime = lastModifiedTime;
    }
}

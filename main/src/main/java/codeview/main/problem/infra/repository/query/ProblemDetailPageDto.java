package codeview.main.problem.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProblemDetailPageDto {

    private Long problemId;
    private String name;
    private String originalProblemFileName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    @QueryProjection
    public ProblemDetailPageDto(Long problemId, String name, String originalProblemFileName,
                                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.problemId = problemId;
        this.name = name;
        this.originalProblemFileName = originalProblemFileName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

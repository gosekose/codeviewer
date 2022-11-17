package codeview.main.problem.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProblemListPageDto {

    private Long problemId;
    private String name;
    private String originalProblemFileName;
    private String originalShellFileName;
    private String originalInputsFileName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    @QueryProjection
    public ProblemListPageDto(Long problemId, String name, String originalProblemFileName,
                              String originalShellFileName, String originalInputsFileName, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.problemId = problemId;
        this.name = name;
        this.originalProblemFileName = originalProblemFileName;
        this.originalShellFileName = originalShellFileName;
        this.originalInputsFileName = originalInputsFileName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

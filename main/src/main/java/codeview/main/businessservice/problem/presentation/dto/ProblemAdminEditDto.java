package codeview.main.businessservice.problem.presentation.dto;

import codeview.main.businessservice.problem.domain.embedded.ProblemFile;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import codeview.main.businessservice.problem.domain.enumtype.ProblemType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProblemAdminEditDto {

    private String name;
    private ProblemType problemType;

    private ProblemFile problemFile;
    private ProblemInputIoFile problemInputIoFile;
    private LocalDateTime openTime;
    private LocalDateTime closedTime;
    private ProblemDifficulty problemDifficulty;
    private Integer totalScore;
    private String problemLanguage;

    @Builder
    public ProblemAdminEditDto(String name, ProblemType problemType, ProblemFile problemFile, ProblemInputIoFile problemInputIoFile, LocalDateTime openTime, LocalDateTime closedTime, ProblemDifficulty problemDifficulty, Integer totalScore, String problemLanguage) {
        this.name = name;
        this.problemType = problemType;
        this.problemFile = problemFile;
        this.problemInputIoFile = problemInputIoFile;
        this.openTime = openTime;
        this.closedTime = closedTime;
        this.problemDifficulty = problemDifficulty;
        this.totalScore = totalScore;
        this.problemLanguage = problemLanguage;
    }

}

package codeview.main.businessservice.problem.presentation.dto;

import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import codeview.main.businessservice.problem.domain.enumtype.ProblemType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProblemClientDto {

    private String name;
    private ProblemType problemType;

    private String problemFileName;
    private String problemInputIoFileName;
    private LocalDateTime openTime;
    private LocalDateTime closedTime;
    private ProblemDifficulty problemDifficulty;
    private Integer totalScore;
    private String problemLanguage;
    private String[] problemLanguages;

    @Builder
    public ProblemClientDto(String name, ProblemType problemType, String problemFileName, String problemInputIoFileName,
                            LocalDateTime openTime, LocalDateTime closedTime, ProblemDifficulty problemDifficulty,
                            Integer totalScore, String problemLanguage, String[] problemLanguages) {
        this.name = name;
        this.problemType = problemType;
        this.problemFileName = problemFileName;
        this.problemInputIoFileName = problemInputIoFileName;
        this.openTime = openTime;
        this.closedTime = closedTime;
        this.problemDifficulty = problemDifficulty;
        this.totalScore = totalScore;
        this.problemLanguage = problemLanguage;
        this.problemLanguages = problemLanguages;
    }

}

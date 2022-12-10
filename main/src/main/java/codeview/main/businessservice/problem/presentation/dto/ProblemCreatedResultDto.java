package codeview.main.businessservice.problem.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProblemCreatedResultDto<T> {

    private T problemId;
    private T problemType;
    private T problemName;
    private T openTime;
    private T closedTime;
    private T problemDifficulty;
    private T descriptions;
    private T inputs;
    private T outputs;
    private T preFilePath;
    private T scores;
    private T totalScore;
    private T problemLanguage;
    private T message;

    @Builder

    public ProblemCreatedResultDto(T problemId, T problemType, T problemName, T openTime, T closedTime, T problemDifficulty,
                                   T descriptions, T inputs, T outputs, T preFilePath, T scores, T totalScore,
                                   T problemLanguage, T message) {
        this.problemId = problemId;
        this.problemType = problemType;
        this.problemName = problemName;
        this.openTime = openTime;
        this.closedTime = closedTime;
        this.problemDifficulty = problemDifficulty;
        this.descriptions = descriptions;
        this.inputs = inputs;
        this.outputs = outputs;
        this.preFilePath = preFilePath;
        this.scores = scores;
        this.totalScore = totalScore;
        this.problemLanguage = problemLanguage;
        this.message = message;
    }
}

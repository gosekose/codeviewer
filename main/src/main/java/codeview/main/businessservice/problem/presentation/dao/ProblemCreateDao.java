package codeview.main.businessservice.problem.presentation.dao;

import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import codeview.main.businessservice.problem.domain.enumtype.ProblemType;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ProblemCreateDao {

    @NotNull
    private ProblemType problemType;

    @NotNull
    @Length(min = 3, max = 100, message = "문제명 길이는 3 ~ 100 사이입니다.")
    private String problemName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    private LocalDateTime openTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    private LocalDateTime closedTime;

    @NotNull
    private ProblemDifficulty problemDifficulty;

    @NotNull
    private List<String> descriptions;

    @NotNull
    private List<String> inputs;

    @NotNull
    private List<String> outputs;

    private MultipartFile problemFile;

    @NotNull
    private String preFilePath;

    private MultipartFile ioZipFile;

    @NotNull
    private List<Integer> scores = new ArrayList<>();

    @NotNull
    @Range(min = 10, max = 100)
    private Integer totalScore;

    @NotNull
    private String problemLanguage;

    @Builder
    public ProblemCreateDao(ProblemType problemType, String problemName, LocalDateTime openTime, LocalDateTime closedTime, ProblemDifficulty problemDifficulty, List<String> descriptions, List<String> inputs, List<String> outputs, MultipartFile problemFile, String preFilePath, MultipartFile ioZipFile, List<Integer> scores, Integer totalScore, String problemLanguage) {
        this.problemType = problemType;
        this.problemName = problemName;
        this.openTime = openTime;
        this.closedTime = closedTime;
        this.problemDifficulty = problemDifficulty;
        this.descriptions = descriptions;
        this.inputs = inputs;
        this.outputs = outputs;
        this.problemFile = problemFile;
        this.preFilePath = preFilePath;
        this.ioZipFile = ioZipFile;
        this.scores = scores;
        this.totalScore = totalScore;
        this.problemLanguage = problemLanguage;
    }
}

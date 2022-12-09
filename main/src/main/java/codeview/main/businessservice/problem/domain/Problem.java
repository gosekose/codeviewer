package codeview.main.businessservice.problem.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.domain.embedded.ProblemFile;
import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.businessservice.problem.domain.enumtype.ProblemDifficulty;
import codeview.main.businessservice.problem.domain.enumtype.ProblemType;
import codeview.main.businessservice.problemdescription.domain.ProblemDescription;
import codeview.main.businessservice.problemdescription.domain.ProblemIoExample;
import codeview.main.businessservice.solve.domain.Solve;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter @NoArgsConstructor
@AllArgsConstructor
public class Problem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "problem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_group_id")
    private MemberGroup memberGroup;

    @OneToMany(mappedBy = "problem")
    private List<Solve> solves = new ArrayList<>();

    @OneToMany(mappedBy = "problem")
    private List<ProblemDescription> problemDescriptions = new ArrayList<>();

    @OneToMany(mappedBy = "problem")
    private List<ProblemIoExample> problemIoExamples = new ArrayList<>();

    private String name;

    @Enumerated(STRING)
    private ProblemType problemType;

    @Embedded
    private ProblemFile problemFile;

    @Embedded
    private ProblemInputIoFile problemInputIoFile;

    private LocalDateTime openTime;
    private LocalDateTime closedTime;

    @Enumerated(STRING)
    private ProblemDifficulty problemDifficulty;

    private Integer totalScore;

    private String allowedLanguage;

    @Builder
    public Problem(MemberGroup memberGroup, String name, ProblemType problemType,
                   ProblemFile problemFile, ProblemInputIoFile problemInputIoFile, LocalDateTime openTime,
                   LocalDateTime closedTime, ProblemDifficulty problemDifficulty,
                   Integer totalScore, String allowedLanguage) {
        this.memberGroup = memberGroup;
        this.name = name;
        this.problemType = problemType;
        this.problemFile = problemFile;
        this.problemInputIoFile = problemInputIoFile;
        this.openTime = openTime;
        this.closedTime = closedTime;
        this.problemDifficulty = problemDifficulty;
        this.totalScore = totalScore;
        this.allowedLanguage = allowedLanguage;
    }

}

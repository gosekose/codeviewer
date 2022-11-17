package codeview.main.problem.domain;

import codeview.main.Solve.domain.Solve;
import codeview.main.auth.domain.BaseEntity;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.domain.embedded.InputFile;
import codeview.main.problem.domain.embedded.ProblemFile;
import codeview.main.problem.domain.embedded.ShellFile;
import codeview.main.problem.domain.embedded.SolvePython;
import codeview.main.problemdescription.domain.ProblemDescription;
import codeview.main.problemdescription.domain.ProblemIoExample;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Embedded
    private ProblemFile problemFile;
    @Embedded
    private ShellFile shellFile;
    @Embedded
    private InputFile inputFile = new InputFile();
    private LocalDateTime openTime;
    private LocalDateTime closedTime;

    private String solveJavaAddress;

    @Embedded
    private SolvePython solvePython;

    @Builder
    public Problem(MemberGroup memberGroup, String name, ProblemFile problemFile, ShellFile shellFile, InputFile inputFile, LocalDateTime openTime, LocalDateTime closedTime) {
        this.memberGroup = memberGroup;
        this.name = name;
        this.problemFile = problemFile;
        this.shellFile = shellFile;
        this.inputFile = inputFile;
        this.openTime = openTime;
        this.closedTime = closedTime;
    }

}

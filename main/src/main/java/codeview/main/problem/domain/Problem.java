package codeview.main.problem.domain;

import codeview.main.Solve.domain.Solve;
import codeview.main.auth.domain.BaseEntity;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problemdescription.domain.ProblemDescription;
import lombok.AllArgsConstructor;
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

    private String storageAddress;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private String description;
    private String solveJavaAddress;
    private String solvePythonAddress;
}

package codeview.main.businessservice.problem.domain;

import codeview.main.auth.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemScore extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "problem_score_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private int number;
    private int score;

    @Builder
    public ProblemScore(Problem problem, int number, int score) {
        this.problem = problem;
        this.number = number;
        this.score = score;
    }
}

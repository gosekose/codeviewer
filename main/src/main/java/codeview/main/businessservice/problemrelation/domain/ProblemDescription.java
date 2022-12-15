package codeview.main.businessservice.problemrelation.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.businessservice.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProblemDescription extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "problem_description_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private int number;
    private String description;


    @Builder
    public ProblemDescription(Problem problem, int number, String description) {
        this.problem = problem;
        this.number = number;
        this.description = description;
    }

    public void updateDescriptions(int number, String description) {
        this.number = number;
        this.description = description;
    }
}

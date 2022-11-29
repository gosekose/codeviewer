package codeview.main.businessservice.problemdescription.domain;

import codeview.main.businessservice.problem.domain.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ProblemIoExample {

    @Id @GeneratedValue
    @Column(name = "io_example_id")
    private Long id;

    private int number;
    private String inputSource;
    private String outputSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Builder
    public ProblemIoExample(int number, String inputSource, String outputSource, Problem problem) {
        this.number = number;
        this.inputSource = inputSource;
        this.outputSource = outputSource;
        this.problem = problem;
    }
}

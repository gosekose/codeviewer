package codeview.main.problemdescription.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.problem.domain.Problem;
import lombok.AllArgsConstructor;
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


}

package codeview.main.solve.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.member.domain.Member;
import codeview.main.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Solve extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "solve_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String codeAddress;

    private int score;


    @Builder
    public Solve(Problem problem, Member member, String codeAddress, int score) {
        this.problem = problem;
        this.member = member;
        this.codeAddress = codeAddress;
        this.score = score;
    }
}
package codeview.main.businessservice.solve.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.problem.domain.Problem;
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

    private int number;

    private String codeAddress;

    private int score;


    @Builder
    public Solve(Problem problem, Member member, int number, String codeAddress, int score) {
        this.problem = problem;
        this.member = member;
        this.number = number;
        this.codeAddress = codeAddress;
        this.score = score;
    }

    public void updateNumber(int number) {
        this.number = number;
    }
}

package codeview.main.businessservice.solve.domain;

import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.solve.domain.enumtype.SolveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LastSolveStatus {

    @Id @GeneratedValue
    private Long id;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(STRING)
    private SolveStatus solveStatus;

    @Builder
    public LastSolveStatus(Problem problem, Member member, SolveStatus solveStatus) {
        this.problem = problem;
        this.member = member;
        this.solveStatus = solveStatus;
    }

    public void updateSolveStatus(SolveStatus solveStatus) {
        this.solveStatus = solveStatus;
    }
}

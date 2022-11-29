package codeview.main.businessservice.similarity.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.businessservice.solve.domain.Solve;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Similarity extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "similarity_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solve_id")
    private Solve mySolves;

    @ManyToOne
    @JoinColumn(name = "solve_comparison_id")
    private Solve comparisonSolves;

    private Double comparisonPercent;
}

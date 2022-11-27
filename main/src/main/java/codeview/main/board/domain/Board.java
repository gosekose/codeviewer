package codeview.main.board.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.board.domain.enumtype.AnonymousCheck;
import codeview.main.board.domain.enumtype.Nondisclosure;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Enumerated(STRING)
    private AnonymousCheck anonymousCheck;

    @Enumerated(STRING)
    private Nondisclosure nondisclosure;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private String boardName;
    private String boardMain;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_group_id")
    private MemberGroup memberGroup;

    @OneToMany(mappedBy = "board")
    private List<BoardMultipartFile> boardMultipartFiles = new ArrayList<>();


    @Builder
    public Board(Member writer, AnonymousCheck anonymousCheck, Nondisclosure nondisclosure,
                 Problem problem, MemberGroup memberGroup, String boardName, String boardMain) {
        this.writer = writer;
        this.anonymousCheck = anonymousCheck;
        this.nondisclosure = nondisclosure;
        this.problem = problem;
        this.memberGroup = memberGroup;
        this.boardName = boardName;
        this.boardMain = boardMain;
    }

    /** 연관 관계 편의 메서드 */
    public void addBoardMultipartFiles(BoardMultipartFile multipartFile) {
        this.boardMultipartFiles.add(multipartFile);
    }

}

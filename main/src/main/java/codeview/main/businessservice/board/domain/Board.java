package codeview.main.businessservice.board.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.businessservice.board.domain.enumtype.AnonymousCheck;
import codeview.main.businessservice.board.domain.enumtype.Nondisclosure;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.domain.MemberGroup;
import codeview.main.businessservice.problem.domain.Problem;
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

    private String viewName;

    @Enumerated(STRING)
    private AnonymousCheck anonymousCheck;

    @Enumerated(STRING)
    private Nondisclosure nondisclosure;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private String boardName;
    @Lob
    private String boardMain;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_group_id")
    private MemberGroup memberGroup;

    @OneToMany(mappedBy = "board")
    private List<BoardMultipartFile> boardMultipartFiles = new ArrayList<>();


    @Builder
    public Board(Member writer, AnonymousCheck anonymousCheck, Nondisclosure nondisclosure,
                 Problem problem, MemberGroup memberGroup, String boardName, String boardMain, String viewName) {
        this.writer = writer;
        this.anonymousCheck = anonymousCheck;
        this.nondisclosure = nondisclosure;
        this.problem = problem;
        this.memberGroup = memberGroup;
        this.boardName = boardName;
        this.boardMain = boardMain;
        this.viewName = viewName;
    }

    /** 연관 관계 편의 메서드 */
    public void addBoardMultipartFiles(BoardMultipartFile multipartFile) {
        this.boardMultipartFiles.add(multipartFile);
    }

}

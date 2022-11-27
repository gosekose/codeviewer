package codeview.main.board.domain;

import codeview.main.auth.domain.BaseEntity;
import codeview.main.common.domain.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardMultipartFile extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_file_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Embedded
    private UploadFile boardFile;

    @Builder
    public BoardMultipartFile(Board board, UploadFile boardFile) {
        this.board = board;
        this.boardFile = boardFile;
    }

}

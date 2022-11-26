package codeview.main.board.infra.repository.query;

import codeview.main.board.domain.enumtype.AnonymousCheck;
import codeview.main.board.domain.enumtype.Nondisclosure;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListDto {

    private Long boardId;
    private String boardName;
    private String boardMain;
    private String writerName;
    private String memberName;
    private AnonymousCheck anonymousCheck;
    private Nondisclosure nondisclosure;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long problemId;
    private String problemName;

    @Builder
    @QueryProjection
    public BoardListDto(Long boardId, String boardName, String boardMain, String writerName, String memberName,
                        AnonymousCheck anonymousCheck, Nondisclosure nondisclosure, LocalDateTime createdAt, LocalDateTime modifiedAt, Long problemId, String problemName) {
        this.boardId = boardId;
        this.boardName = boardName;
        this.boardMain = boardMain;
        this.writerName = writerName;
        this.memberName = memberName;
        this.anonymousCheck = anonymousCheck;
        this.nondisclosure = nondisclosure;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.problemId = problemId;
        this.problemName = problemName;
    }
}

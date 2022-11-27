package codeview.main.board.infra.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListDto {

    private Long boardId;
    private String boardName;

    private String viewName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long problemId;
    private String problemName;

    @Builder
    @QueryProjection
    public BoardListDto(Long boardId, String boardName, String viewName, LocalDateTime createdAt, LocalDateTime modifiedAt, Long problemId, String problemName) {
        this.boardId = boardId;
        this.boardName = boardName;
        this.viewName = viewName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.problemId = problemId;
        this.problemName = problemName;
    }
}

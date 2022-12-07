package codeview.main.businessservice.problem.presentation.dto;

import codeview.main.businessservice.problem.domain.enumtype.ProblemType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProblemAdminEditDto {

    private String name;
    private ProblemType problemType;
    private LocalDateTime openTime;
    private LocalDateTime closedTime;

    @Builder
    public ProblemAdminEditDto(String name, ProblemType problemType, LocalDateTime openTime, LocalDateTime closedTime) {
        this.name = name;
        this.problemType = problemType;
        this.openTime = openTime;
        this.closedTime = closedTime;
    }
}

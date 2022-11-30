package codeview.main.serverconnect.presentation.dto;

import codeview.main.serverconnect.domain.TestStatus;
import lombok.Data;

@Data
public class SolveResponseDto {

    private Long solveId;
    private Integer score;
    private TestStatus testStatus;
}

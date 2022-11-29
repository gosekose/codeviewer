package codeview.main.serverconnect.presentation.dto;

import lombok.Data;

@Data
public class SolveRequestDto {

    private String problemUrl;
    private Long solveId;
    private String solveRequestUrl;

}

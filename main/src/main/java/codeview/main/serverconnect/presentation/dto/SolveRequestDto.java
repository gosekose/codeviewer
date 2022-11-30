package codeview.main.serverconnect.presentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class SolveRequestDto {

    private String problemUrl;
    private String solveRequestUrl;
    private Long solveId;
    private List<Integer> score;

}

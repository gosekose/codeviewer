package codeview.main.businessservice.problem.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ServerIoFilePathDto {

    private String mainFilePath;
    private IoFileDataDto ioFileDataDto;
    private List<Integer> scores;

    @Builder
    public ServerIoFilePathDto(String mainFilePath, IoFileDataDto ioFileDataDto, List<Integer> scores) {
        this.mainFilePath = mainFilePath;
        this.ioFileDataDto = ioFileDataDto;
        this.scores = scores;
    }
}

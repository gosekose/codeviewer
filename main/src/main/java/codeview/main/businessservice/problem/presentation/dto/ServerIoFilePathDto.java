package codeview.main.businessservice.problem.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ServerIoFilePathDto {

    private String mainFilePath;
    private IoFilePathDto ioFilePathDto;

    @Builder
    public ServerIoFilePathDto(String mainFilePath, IoFilePathDto ioFilePathDto) {
        this.mainFilePath = mainFilePath;
        this.ioFilePathDto = ioFilePathDto;
    }
}

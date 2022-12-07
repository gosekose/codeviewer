package codeview.main.businessservice.problem.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ServerIoFilePathDto {

    private String mainFilePath;
    private IoFileDataDto ioFileDataDto;

    @Builder
    public ServerIoFilePathDto(String mainFilePath, IoFileDataDto ioFileDataDto) {
        this.mainFilePath = mainFilePath;
        this.ioFileDataDto = ioFileDataDto;
    }
}

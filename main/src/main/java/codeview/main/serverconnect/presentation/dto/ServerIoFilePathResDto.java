package codeview.main.serverconnect.presentation.dto;

import lombok.Data;

@Data
public class ServerIoFilePathResDto {

    private boolean ioFileResult;
    private boolean ioProcessResult;
    private boolean runtimeResult;
    private boolean serverResult;

}

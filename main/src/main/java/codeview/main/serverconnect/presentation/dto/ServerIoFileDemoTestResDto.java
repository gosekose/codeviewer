package codeview.main.serverconnect.presentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServerIoFileDemoTestResDto {

    private boolean ioFileResult;
    private boolean ioProcessResult;
    private boolean runtimeResult;
    private boolean serverResult;
    private int totalScore;
    private List<Boolean> totalStatus;

}

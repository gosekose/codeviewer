package codeview.main.businessservice.problem.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class DockerIoFilePathDto {

    private String docker;
    private IoFilePathDto ioFilePathDto;

    @Builder
    public DockerIoFilePathDto(String docker, IoFilePathDto ioFilePathDto) {
        this.docker = docker;
        this.ioFilePathDto = ioFilePathDto;
    }
}

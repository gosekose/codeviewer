package codeview.main.businessservice.problem.infra.util;

import codeview.main.businessservice.problem.domain.embedded.ProblemInputIoFile;
import codeview.main.common.domain.UploadFile;
import codeview.main.businessservice.problem.domain.embedded.ProblemFile;
import codeview.main.businessservice.problem.domain.embedded.ShellFile;
import org.springframework.stereotype.Component;

@Component
public class FileConverter {

    public static ProblemInputIoFile toInputFile(UploadFile uploadFile) {

        if (uploadFile == null) {
            return null;
        }
        return ProblemInputIoFile.builder()
                .inputStoreFolderPath(uploadFile.getStoreFileName())
                .build();
    }

    public static ProblemFile toProblemFile(UploadFile uploadFile) {

        if (uploadFile == null) {
            return null;
        }
        return ProblemFile.builder()
                .problemUploadName(uploadFile.getUploadFileName())
                .problemStoreName(uploadFile.getStoreFileName())
                .build();
    }

    public static ShellFile toShellFile(UploadFile uploadFile) {

        if (uploadFile == null) {
            return null;
        }
        return ShellFile.builder()
                .shellUploadName(uploadFile.getUploadFileName())
                .shellStoreName(uploadFile.getStoreFileName())
                .build();
    }

}

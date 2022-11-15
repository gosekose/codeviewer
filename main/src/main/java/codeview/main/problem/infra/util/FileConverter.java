package codeview.main.problem.infra.util;

import codeview.main.problem.domain.UploadFile;
import codeview.main.problem.domain.embedded.InputFile;
import codeview.main.problem.domain.embedded.ProblemFile;
import codeview.main.problem.domain.embedded.ShellFile;
import org.springframework.stereotype.Component;

@Component
public class FileConverter {

    public static InputFile toInputFile(UploadFile uploadFile) {

        if (uploadFile == null) {
            return null;
        }
        return InputFile.builder()
                .inputUploadName(uploadFile.getUploadFileName())
                .inputStoreName(uploadFile.getStoreFileName())
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

package codeview.main.businessservice.problem.infra.util.filestore;

import codeview.main.businessservice.problem.presentation.dto.IoFileDataDto;
import codeview.main.common.application.FolderRemover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class IoFileStore extends AbstractFileStore {

    private final FolderRemover folderRemover;

    public IoFileDataDto makeIoFileDataDto(Path path) {

        try{
            IoFileDataDto ioFileDataDto = new IoFileDataDto();
            ioFileDataDto.setFolderPath(String.valueOf(path));

            File fileUri = new File(path.toUri());
            File[] files = fileUri.listFiles();
            Arrays.sort(files);


            for (File file : files) {
                if(file.isFile()) {

                    String[] pathString = file.getPath().split("\\.");
                    if (pathString.length != 0) {
                        if (pathString[pathString.length-1].equals("in")) {
                            ioFileDataDto.addInputs(pathLastFileName(file));

                        } else if (pathString[pathString.length-1].equals("out")) {
                            ioFileDataDto.addOutputs(pathLastFileName(file));
                        }
                    }
                }
            }

            if (ioFileDataDto.getInputs().size() == 0 || ioFileDataDto.getOutputs().size() == 0 ||
                    ioFileDataDto.getInputs().size() != ioFileDataDto.getOutputs().size()) {
                folderRemover.removePart(new File(ioFileDataDto.getFolderPath()));return null;}

            return ioFileDataDto;

        } catch (Exception e) {
            return null;
        }

    }

    private static String pathLastFileName(File file) {
        String[] split = file.getPath().split("/");
        return split[split.length - 1];
    }

}

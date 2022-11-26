package codeview.main.problem.infra.util.filestore;

import codeview.main.common.application.FolderMaker;
import codeview.main.common.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public abstract class AbstractFileStore implements FileStore {

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public UploadFile storeFile(MultipartFile multipartFile, String groupId, String uuid) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        log.info("originalFileName = {}", originalFileName);

        String newProblemFolder = createNewProblemFolder(groupId, uuid);
        log.info("newProblemFolder = {}", newProblemFolder);

        String storeFileName = createStoreFileName(newProblemFolder, originalFileName);
        log.info("storeFileName = {}", storeFileName);

        multipartFile.transferTo(new File(storeFileName));

        return UploadFile.builder()
                .uploadFileName(originalFileName)
                .storeFileName(storeFileName)
                .build();
    }

    @Override
    public String createStoreFileName(String newProblemFolder, String originalFileName) {
        return  newProblemFolder + "/" + originalFileName;
    }

    @Override
    public String createNewProblemFolder(String groupId, String uuid) {

        return FolderMaker.folderMaker(fileDir + "/" + groupId, uuid);


    }

    //    @Override
//    public String extractExt(String originalFileName) {
//
//        int pos = originalFileName.lastIndexOf(".");
//        return originalFileName.substring(pos + 1);
//
//    }


}

package codeview.main.businessservice.problem.infra.util.filestore;

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

        String originalFileName = getOrigianlFileName(multipartFile);
        if (originalFileName == null) return null;

        String newProblemFolder = createNewProblemFolder(groupId, uuid);
        log.info("newProblemFolder = {}", newProblemFolder);

        return getUploadFile(multipartFile, originalFileName, newProblemFolder);
    }
    @Override
    public UploadFile storeFileAlreadyFolder(MultipartFile multipartFile, String groupId, String path) throws IOException {

        String originalFileName = getOrigianlFileName(multipartFile);
        if (originalFileName == null) return null;

        return getUploadFile(multipartFile, originalFileName, path);
    }

    @Override
    public String createStoreFileName(String newProblemFolder, String originalFileName) {
        return  newProblemFolder + "/" + originalFileName;
    }

    @Override
    public String createNewProblemFolder(String groupId, String uuid) {

        return FolderMaker.folderMaker(fileDir + "/" + groupId, uuid);

    }

    private static String getOrigianlFileName(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        log.info("originalFileName = {}", originalFileName);
        return originalFileName;
    }

    private UploadFile getUploadFile(MultipartFile multipartFile, String originalFileName, String newProblemFolder) throws IOException {
        String storeFileName = createStoreFileName(newProblemFolder, originalFileName);
        log.info("storeFileName = {}", storeFileName);

        multipartFile.transferTo(new File(storeFileName));

        return UploadFile.builder()
                .uploadFileName(originalFileName)
                .storeFileName(storeFileName)
                .build();
    }

}

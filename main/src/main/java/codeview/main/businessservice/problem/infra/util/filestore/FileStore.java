package codeview.main.businessservice.problem.infra.util.filestore;

import codeview.main.common.domain.UploadFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public interface FileStore {

    UploadFile makeStoreFolder(MultipartFile multipartFile, String groupId, String newProblemFolder) throws IOException;
    UploadFile retainAlreadyFolder(MultipartFile multipartFile, String groupId, String path) throws IOException;

    UploadFile updateUploadFileForEdit(MultipartFile multipartFile, String path) throws IOException;

    String createStoreFileName(String newProblemPath, String originalFileName);

    String updateNewProblemFolderAndRetainFolder(String alreadyPath);

    String createNewProblemFolder(String groupId, String uuid);

    void copyFile(String originalFilePath, String newFilePath) throws IOException;

}

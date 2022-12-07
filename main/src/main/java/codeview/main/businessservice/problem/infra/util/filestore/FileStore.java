package codeview.main.businessservice.problem.infra.util.filestore;

import codeview.main.common.domain.UploadFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public interface FileStore {

    UploadFile storeFile(MultipartFile multipartFile, String groupId, String newProblemFolder) throws IOException;
    UploadFile storeFileAlreadyFolder(MultipartFile multipartFile, String groupId, String path) throws IOException;

    String createStoreFileName(String newProblemPath, String originalFileName);

    String createNewProblemFolder(String groupId, String uuid);


}

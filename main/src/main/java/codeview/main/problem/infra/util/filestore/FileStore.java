package codeview.main.problem.infra.util.filestore;

import codeview.main.problem.domain.UploadFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public interface FileStore {

    UploadFile storeFile(MultipartFile multipartFile, String groupId) throws IOException;

    String createStoreFileName(String newProblemPath, String originalFileName);

    String createNewProblemFolder(String groupId);

}

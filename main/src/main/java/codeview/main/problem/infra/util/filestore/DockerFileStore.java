package codeview.main.problem.infra.util.filestore;


import codeview.main.problem.domain.UploadFile;
import codeview.main.problem.presentation.dao.ProblemDockerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DockerFileStore extends AbstractFileStore{


    public List<UploadFile> dockerStoreFile(ProblemDockerDao dao, String groupId, String uuid) throws IOException {

        List<UploadFile> result = new ArrayList<>();
        MultipartFile[] problemFiles = new MultipartFile[] { dao.getMainSource(), dao.getIoFileZip() };

        log.info("problems = {}", problemFiles.length);


        String newProblemFolder = createNewProblemFolder(groupId, uuid);

        log.info("newProblemFolder = {}", newProblemFolder);

        for (MultipartFile multipartFile : problemFiles) {
            if (multipartFile.isEmpty()) {
                return null;
            }
            String originalFileName = multipartFile.getOriginalFilename();
            log.info("originalFileName = {}", originalFileName);

            String storeFileName = createStoreFileName(newProblemFolder, originalFileName);
            log.info("storeFileName = {}", storeFileName);

            multipartFile.transferTo(new File(storeFileName));
            log.info("transferTo clear");

            UploadFile uploadFile = UploadFile.builder()
                    .uploadFileName(originalFileName)
                    .storeFileName(storeFileName)
                    .build();

            result.add(uploadFile);

        }

        log.info("result clear");

        return  result;
    }
}

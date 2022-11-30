package codeview.main.businessservice.problem.presentation.dao;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProblemIoFileDao {

    private String preFilePath;
    private MultipartFile ioZipFile;

}

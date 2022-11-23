package codeview.main.problem.presentation.dao;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProblemIoFileDao {

    private MultipartFile ioZipFile;

}

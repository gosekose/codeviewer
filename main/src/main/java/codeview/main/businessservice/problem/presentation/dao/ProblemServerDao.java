package codeview.main.businessservice.problem.presentation.dao;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProblemServerDao {

    MultipartFile problemFile;
    MultipartFile ioZipFile;

    List<Integer> scores = new ArrayList<>();

}

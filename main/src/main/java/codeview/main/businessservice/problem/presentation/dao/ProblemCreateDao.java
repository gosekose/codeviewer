package codeview.main.businessservice.problem.presentation.dao;

import codeview.main.businessservice.problem.domain.enumtype.ProblemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemCreateDao {

    private ProblemType problemType;
    private String problemName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime openTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime closedTime;

    private List<String> descriptions;
    private List<String> inputs;
    private List<String> outputs;
    private MultipartFile problemFile;
    private String preFilePath;
    private MultipartFile ioZipFile;
    private List<Integer> scores = new ArrayList<>();

}

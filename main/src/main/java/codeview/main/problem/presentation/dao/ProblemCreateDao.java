package codeview.main.problem.presentation.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemCreateDao {

    private String name;
    private LocalDateTime openTime;
    private LocalDateTime closedTime;
    private List<String> descriptions;
    private List<String> inputs;
    private List<String> outputs;
    private MultipartFile problemFile;

}

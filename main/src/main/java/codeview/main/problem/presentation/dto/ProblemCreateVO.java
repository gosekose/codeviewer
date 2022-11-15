package codeview.main.problem.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemCreateVO {

    private String name;
    private List<String> descriptions;
    private List<String> inputs;
    private List<String> outputs;
    private MultipartFile problemFile;
    private MultipartFile shellFile;
    private MultipartFile inputFile;

}

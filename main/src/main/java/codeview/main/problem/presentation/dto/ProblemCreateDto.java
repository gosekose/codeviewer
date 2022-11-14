package codeview.main.problem.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemCreateDto<T> {

    private String name;
    private List<String> descriptions;
    private List<String> inputs;
    private List<String> outputs;
//    private File problemCode;
//    private File testCode;
//    private File inputFile;
}

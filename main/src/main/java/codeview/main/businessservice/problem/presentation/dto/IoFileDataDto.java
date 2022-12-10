package codeview.main.businessservice.problem.presentation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IoFileDataDto {

    private String folderPath;

    private String uploadName;

    private List<String> inputs = new ArrayList<>();
    private List<String> outputs = new ArrayList<>();

    private List<Integer> scores = new ArrayList<>();

    public void addInputs(String path) {
        inputs.add(path);
    }

    public void addOutputs(String path) {
        outputs.add(path);
    }
    public void addScore(int score) { scores.add(score);}
}

package codeview.main.businessservice.problem.presentation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IoFilePathDto {

    private List<String> inputs = new ArrayList<>();
    private List<String> outputs = new ArrayList<>();


    public void addInputs(String path) {
        inputs.add(path);
    }


    public void addOutputs(String path) {
        outputs.add(path);
    }
}

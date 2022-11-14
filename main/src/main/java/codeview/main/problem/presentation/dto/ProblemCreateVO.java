package codeview.main.problem.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemCreateVO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("descriptions")
    private List<DescriptionsVO> descriptions;
    private List<InputsVO> inputs;
    private List<OutputsVO> outputs;

    @Getter @Setter @NoArgsConstructor
    public static class DescriptionsVO {

        private String descriptions;

    }

    @Getter @Setter @NoArgsConstructor
    public static class InputsVO {

        private String inputs;

    }

    @Getter @Setter @NoArgsConstructor
    public static class OutputsVO {

        private String outputs;

    }


}

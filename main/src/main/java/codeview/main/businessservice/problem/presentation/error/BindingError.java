package codeview.main.businessservice.problem.presentation.error;

import codeview.main.businessservice.problem.presentation.dto.ProblemCreatedResultDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class BindingError {

    public static ProblemCreatedResultDto getProblemCreatedResult(BindingResult bindingResult) {
        return ProblemCreatedResultDto
                .builder()
                .message("bindingError")
                .problemType(bindingResult.getFieldValue("problemType"))
                .problemName(bindingResult.getFieldValue("problemName"))
                .openTime(bindingResult.getFieldValue("openTime"))
                .closedTime(bindingResult.getFieldValue("closedTime"))
                .problemDifficulty(bindingResult.getFieldValue("problemDifficulty"))
                .descriptions(bindingResult.getFieldValue("descriptions"))
                .inputs(bindingResult.getFieldValue("inputs"))
                .outputs(bindingResult.getFieldValue("outputs"))
                .scores(bindingResult.getFieldValue("scores"))
                .totalScore(bindingResult.getFieldValue("totalScore"))
                .problemLanguage((bindingResult.getFieldValue("problemLanguage")))
                .build();
    }

}

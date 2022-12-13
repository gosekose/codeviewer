package codeview.main.businessservice.problem.presentation.error;

import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problem.presentation.dto.ProblemCreatedResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ProblemError {

    public ProblemCreatedResultDto getProblemCreatedResult(BindingResult bindingResult) {
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

    public boolean problemCreateValidator(ProblemCreateDao problemCreateDao) {
        int sumScore = 0;
        for (int score : problemCreateDao.getScores()) {
            sumScore += score;
        }

        if (sumScore != problemCreateDao.getTotalScore()) {
            return false;
        }

        return true;
    }

    public ResponseEntity checkCreateError(ProblemCreateDao problemCreateDao, BindingResult bindingResult) {

        if (problemCreateDao.getProblemFile() == null || problemCreateDao.getProblemFile().isEmpty() ||
                problemCreateDao.getIoZipFile() == null || problemCreateDao.getIoZipFile().isEmpty()
        ) {
            return new ResponseEntity<>(ProblemCreatedResultDto.builder().message("파일 업로드는 반드시 필요합니다.").build(), HttpStatus.BAD_REQUEST);
        }

        ResponseEntity responseEntity = checkCommonError(problemCreateDao, bindingResult);

        return responseEntity;
    }

    public ResponseEntity checkCommonError(ProblemCreateDao problemCreateDao, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(getProblemCreatedResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (!problemCreateValidator(problemCreateDao)) {
            return new ResponseEntity<>(ProblemCreatedResultDto.builder().message("총합 점수가 다릅니다.").build(), HttpStatus.BAD_REQUEST);
        }

        if (problemCreateDao.getInputs().size() != problemCreateDao.getOutputs().size()) {
            return new ResponseEntity<>(ProblemCreatedResultDto.builder().message("입출력 예시의 개수가 다릅니다.").build(), HttpStatus.BAD_REQUEST);
        }

        return null;
    }


}

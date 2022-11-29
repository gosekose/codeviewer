package codeview.main.businessservice.problem.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProblemIdDto<T> {

    private T problemId;

    @Builder
    public ProblemIdDto(T problemId) {
        this.problemId = problemId;
    }
}

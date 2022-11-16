package codeview.main.problem.domain.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class SolvePython {

    private String solveUploadName;
    private String solveStoreName;

    @Builder
    public SolvePython(String solveUploadName, String solveStoreName) {
        this.solveUploadName = solveUploadName;
        this.solveStoreName = solveStoreName;
    }
}
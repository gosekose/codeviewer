package codeview.main.businessservice.problem.domain.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class ProblemFile {

    private String problemUploadName;
    private String problemStoreName;

    private String problemFileHash;

    @Builder
    public ProblemFile(String problemUploadName, String problemStoreName) {
        this.problemUploadName = problemUploadName;
        this.problemStoreName = problemStoreName;
    }

    public void updateHash(String problemFileHash) {
        this.problemFileHash = problemFileHash;
    }
}

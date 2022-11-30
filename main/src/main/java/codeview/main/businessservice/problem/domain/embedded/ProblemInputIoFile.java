package codeview.main.businessservice.problem.domain.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class ProblemInputIoFile {

    private String inputStoreFolderPath;

    @Builder
    public ProblemInputIoFile(String inputStoreFolderPath) {
        this.inputStoreFolderPath = inputStoreFolderPath;
    }
}
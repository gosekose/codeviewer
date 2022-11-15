package codeview.main.problem.domain.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class InputFile {

    private String inputUploadName;
    private String inputStoreName;

    @Builder
    public InputFile(String inputUploadName, String inputStoreName) {
        this.inputUploadName = inputUploadName;
        this.inputStoreName = inputStoreName;
    }
}

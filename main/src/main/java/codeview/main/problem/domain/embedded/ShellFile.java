package codeview.main.problem.domain.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class ShellFile {

    private String shellUploadName;
    private String shellStoreName;

    @Builder
    public ShellFile(String shellUploadName, String shellStoreName) {
        this.shellUploadName = shellUploadName;
        this.shellStoreName = shellStoreName;
    }
}

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
    private String uploadZipFileName;

    private String uploadZipFileHash;

    @Builder
    public ProblemInputIoFile(String inputStoreFolderPath, String uploadZipFileName) {
        this.inputStoreFolderPath = inputStoreFolderPath;
        this.uploadZipFileName = uploadZipFileName;
    }

    public void updateHash(String uploadZipFileHash) {
        this.uploadZipFileHash = uploadZipFileHash;
    }
}

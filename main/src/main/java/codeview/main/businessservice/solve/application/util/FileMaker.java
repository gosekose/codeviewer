package codeview.main.businessservice.solve.application.util;

import codeview.main.businessservice.problem.domain.enumtype.ProblemLanguage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface FileMaker<T> {

    void makeFileMaker(String path, String fileName, String text) throws IOException;

    String getFilenameExtension(ProblemLanguage problemLanguage);
    
    String getFileName(ProblemLanguage problemLanguage, String text);

}

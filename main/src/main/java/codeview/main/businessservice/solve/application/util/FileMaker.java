package codeview.main.businessservice.solve.application.util;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public interface FileMaker<T> {

    void makeFileMaker(String path, String fileName, String text) throws IOException;

}

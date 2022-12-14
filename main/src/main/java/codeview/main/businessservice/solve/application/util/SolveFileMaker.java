package codeview.main.businessservice.solve.application.util;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public abstract class SolveFileMaker implements FileMaker {

    @Override
    public void makeFileMaker(String path, String fileName, String text) throws IOException {

        path = path + "/" + fileName;

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(text.getBytes());

        bufferedOutputStream.close();
    }

}

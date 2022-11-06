package codeview.main.test.application;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class TestCompileService {

    public void makeJavaSource(String source) throws IOException {

        File fileSource = new File("/home/source/test.java");
        File shellSource = new File("/home/source/test.sh");

        FileWriter fw = new FileWriter(fileSource);
        BufferedWriter writer = new BufferedWriter(fw);

        writer.write(source);
        writer.close();

        fw = new FileWriter(shellSource);
        writer = new BufferedWriter(fw);

        writer.write("java HomeMain.java");
        writer.close();

    }

}

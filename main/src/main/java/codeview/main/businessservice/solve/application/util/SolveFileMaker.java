package codeview.main.businessservice.solve.application.util;

import codeview.main.businessservice.problem.domain.enumtype.ProblemLanguage;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String getFilenameExtension(ProblemLanguage problemLanguage) {

        if (problemLanguage.equals(ProblemLanguage.python3)) {
            return ".py";
        } else if (problemLanguage.equals(ProblemLanguage.java11)) {
            return ".java";
        }
        return null;
    }

    @Override
    public String getFileName(ProblemLanguage problemLanguage, String text) {

        if (text == null || problemLanguage == null) {
            return null;
        }

        if (problemLanguage.equals(ProblemLanguage.python3)) {
            return "main.py";
        } else if (problemLanguage.equals(ProblemLanguage.java11)) {

            String source = URLDecoder.decode(text, StandardCharsets.UTF_8);

            int breakCnt = 0;
            boolean breakPoint = false;
            String className = "";

            String[] textSplit = source.split(" ");

            for (int i=0; i<textSplit.length; i++) {

                if (breakPoint) {
                    className = textSplit[i];
                }

                if (textSplit[i] == "class") {
                    breakPoint = true;
                }

                if (textSplit[i] == "main") {
                    return className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase() + ".java";
                }

                List<String> a= new ArrayList<>();


            }


            return "";
        }

        return null;
    }
}

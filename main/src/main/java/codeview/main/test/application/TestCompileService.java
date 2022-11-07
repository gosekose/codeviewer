package codeview.main.test.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class TestCompileService {

    public void makeJavaSource(HttpSession session, String source) throws IOException {

        String decoderResult = URLDecoder.decode(source, StandardCharsets.UTF_8).substring(50);

        String[] sourceArray = decoderResult.split(" ");

        String className = "";

        boolean breakPoint = false;
        int changeIndex = 0;

        // 클래스명 바꾸기
        for(int i=0; i<sourceArray.length; i++) {
            className = sourceArray[i];
            changeIndex = i;

            if(breakPoint) {
                break;
            }

            if(sourceArray[i].equals("class")) {
                breakPoint = true;
            }
        }

        if (className == "") {
            return;
        }

        className = "T_" + session.getId() + "_" + className;
        sourceArray[changeIndex] = className;
        String newDecoderResult = String.join(" ", sourceArray);

        // 파일 입출력
        String path  = "/home/koseyun/IdeaProjects/capston/main/source/index/" + className + ".java";
        String shellPath = "/home/koseyun/IdeaProjects/capston/main/source/index/" +className + ".sh";

        File fileSource = new File(path);
        File shellSource = new File(shellPath);

        FileWriter fw = new FileWriter(fileSource);
        BufferedWriter writer = new BufferedWriter(fw);

        writer.write(newDecoderResult);
        writer.close();

        fw = new FileWriter(shellSource);
        writer = new BufferedWriter(fw);

        writer.write("javac ");
        writer.write(className);
        writer.write(".java");
        writer.close();

    }

}

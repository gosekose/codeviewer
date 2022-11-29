package codeview.main.businessservice.indextest.presentation;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

public class Test3 {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String filePath1 = "/home/koseyun/IdeaProjects/capston/main/main/src/main/java/codeview/main/indextest/application/storage/Test2.java";

        createTempFile();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, System.out, System.out, filePath1);

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {new File(filePath1).toURI().toURL()});


        System.out.println(" compile test");


    }

    public static void createTempFile() throws IOException {

        String filePath = "/home/koseyun/IdeaProjects/capston/main/main/src/main/java/codeview/main/indextest/presentation/Test2.java";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        String newFileName = "/home/koseyun/IdeaProjects/capston/main/main/src/main/java/codeview/main/indextest/application/storage/" + "Test2.java";
        File file = new File(newFileName);

        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);

        String reader;
        int i = 0;
        while((reader = bufferedReader.readLine()) != null) {
            if (i==0) {
                writer.write("package codeview.main.indextest.application.storage;" + '\n');
            } else {
                writer.write(reader + '\n');
            }
            i++;

            System.out.println("reader = " + reader);
        }

        writer.write("rrrr");

        writer.close();
    }
}

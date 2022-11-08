package codeview.main.indextest.presentation;


import org.springframework.beans.factory.annotation.Value;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

public class TestIndexCompileService {

    @Value("${source.path.index}")
    static String baseUrl;


    public static void main(String[] args) {

        System.out.println("test clear");
    }

    public Object createInstance(String source) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        File sourceFile = new File(baseUrl + "DynamicClass.java");

        new FileWriter(sourceFile).append(source).close();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, System.out, System.out, sourceFile.getPath());

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]
                {new File(baseUrl).toURI().toURL()});

        Class<?> cls = Class.forName(baseUrl, true, classLoader);
        Constructor<?> constructor = cls.getConstructor(null);

        return constructor.newInstance();

    }

}

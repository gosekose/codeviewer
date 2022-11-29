package codeview.main.businessservice.indextest.application.sourcebuilder;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class DynamicClassBuilder {


    public void createCompile(String sourcePath, String body) {

        try {
            File sourceFile = new File(sourcePath);

            String source = this.getSource(body);

            new FileWriter(sourceFile).append(source).close();


            // 만들어진 Java 파일을 컴파일한다.
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, System.out, System.out, sourceFile.getPath());
        } catch (Exception e) {
            new MyClassNotFoundException();
        }
    }


    public Object createInstance(String body) throws ClassNotFoundException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, MalformedURLException {

        String sourcePath = "/home/koseyun/IdeaProjects/capston/main/main/src/main/java/codeview/main/indextest/application/storage";

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {new File(sourcePath).toURI().toURL()});
        Class<?> cls = Class.forName("codeview.main.indextest.application.storage.DynamicClass", true, classLoader);
        Constructor<?> constructor = cls.getConstructor();

        return constructor.newInstance();

    }


    public String runObject(Object obj, char[] params) throws Exception {
        String methodName = "runMethod";
        Class arguments[] = new Class[] {params.getClass()};

        // Source를 만들때 지정한 Method를 실행
        Method objMethod = obj.getClass().getMethod(methodName, arguments);
        Object result = objMethod.invoke(obj, params);
        return (String) result;
    }


    private String getSource(String body) {
        StringBuffer sb = new StringBuffer();

        // Java Source를 생성한다.
        sb.append("package codeview.main.indextest.application.storage; ")
                .append(body);
        return sb.toString();
    }
}

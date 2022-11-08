package codeview.main.indextest.application.sourcebuilder;

public class MyClassNotFoundException extends ClassNotFoundException {
    public MyClassNotFoundException() {
        System.out.println(" system reload");
    }

    public MyClassNotFoundException(String s, Throwable ex) {
        super(s, ex);
    }
}

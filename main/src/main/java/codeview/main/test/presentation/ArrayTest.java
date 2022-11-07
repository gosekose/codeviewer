package codeview.main.test.presentation;

public class ArrayTest {

    public static void main(String[] args) {

        String[] test = {"apple", "banana", "melon"};

        test[1] = "mandu";

        String join = String.join(" ", test);
        System.out.println("join = " + join);

    }
}

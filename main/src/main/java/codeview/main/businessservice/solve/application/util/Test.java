package codeview.main.businessservice.solve.application.util;


public class Test {

    public static void main(String[] args) {

        String[] a = new String[3];


        a[0] = "1";

        if (a[1] == null) {
            System.out.println("test");
        }


        System.out.println("a[1] + a[2] = " + a[1] + a[2]);


        String aa = "derfkslldlEvwDsdlvw";

        String s = aa.substring(0, 1).toUpperCase() + aa.substring(1).toLowerCase();

        System.out.println(s);


        String t = "tekds";

        String tt = "1234tekds";

        boolean contains = tt.contains(t);

        System.out.println("contains = " + contains);

        boolean b = tt.startsWith(t);

        int i = tt.indexOf(t);

        System.out.println("i = " + i);

        String substring = tt.substring(0, tt.length() - t.length());

        System.out.println("substring = " + substring);


    }

}

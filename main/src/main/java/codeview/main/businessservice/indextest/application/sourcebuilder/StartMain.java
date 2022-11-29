package codeview.main.businessservice.indextest.application.sourcebuilder;


public class StartMain {
    public static void main(String[] args) throws Exception {

        DynamicClassBuilder dcb = new DynamicClassBuilder();

        // 문자열로 전달 받은 실행할 Java 문법
        String body = "public class DynamicClass { public String runMethod(char[] params) throws Exception { return \"dassssaassd3\";}}";

        // 위 문자열을 기준으로 생성된 Class를 Object로 생성
        Object obj = dcb.createInstance(body);

//        char params[] = new char[] {'c', 's'};
//
//        // 실행 후 결과 전달 받음
//        String rst = dcb.runObject(obj, params);
//
//        rst = dcb.runObject(obj, params);
//        if (rst == null) {
//            System.out.println("test");
//        } else {
//            System.out.println(" test2 ");
//        }
//
//        System.out.println("rst = " + rst);

    }
}

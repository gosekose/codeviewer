package codeview.main.indextest.presentation;

import java.time.LocalDateTime;
import java.util.concurrent.*;


public class Test {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> future = executorService.submit(
                () -> {
                    System.out.println("Start Time= " + LocalDateTime.now());

                    int sum = 1 + 1;
                    Thread.sleep(4000);
                    System.out.println("End Time = " + LocalDateTime.now());
                    return sum;
                }
        );

        Integer result;

        try {

            result = future.get(2000, TimeUnit.MILLISECONDS);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true);
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new RuntimeException(e);

        } finally {
            executorService.shutdown();
            System.out.println("system close");
        }

    }

}

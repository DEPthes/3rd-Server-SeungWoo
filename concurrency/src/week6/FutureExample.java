package week6;

import java.util.concurrent.*;

public class FutureExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<String> task = () -> {
            TimeUnit.SECONDS.sleep(1);
            return "Hello, Future!";
        };

        Future<String> future = executor.submit(task);

        // Blocking call to get the result
        String result = future.get();
        System.out.println(result);

        executor.shutdown();
    }
}


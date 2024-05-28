package week6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {
    public static void main(String[] args) {
        // 비동기 작업 실행
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1); // 비동기 작업 수행
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Hello, CompletableFuture!";
        });

        // 콜백 등록
        future.thenApply(result -> result + " - Processed")
                .thenAccept(result -> System.out.println("Result: " + result))
                .exceptionally(ex -> {
                    System.err.println("Exception: " + ex);
                    return null;
                });

        // 비동기 작업 결과를 기다리지 않고 다른 작업 수행 가능
        System.out.println("Doing other work...");

        // 결과를 비동기적으로 기다림
        future.join(); // 이 줄이 비동기 작업이 완료될 때까지 블로킹
    }
}

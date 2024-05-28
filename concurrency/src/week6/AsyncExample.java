package week6;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class AsyncExample {
    public static void main(String[] args) {
        Flux<String> flux = Flux.just("Hello", "Reactor", "Flux")
                .subscribeOn(Schedulers.parallel());

        flux.subscribe(
                data -> System.out.println("Received: " + data),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Completed")
        );

        Mono<String> mono = Mono.just("Hello, Reactor!")
                .subscribeOn(Schedulers.parallel());

        mono.subscribe(
                data -> System.out.println("Received: " + data),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Completed")
        );

        // 메인 스레드가 바로 종료되는 것을 방지하기 위해 잠시 대기
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


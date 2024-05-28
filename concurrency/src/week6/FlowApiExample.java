package week6;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class FlowApiExample {
    public static void main(String[] args) throws InterruptedException {
        // Publisher 생성
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Subscriber 등록
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1); // 데이터 요청
            }

            @Override
            public void onNext(String item) {
                System.out.println("Received: " + item);
                subscription.request(1); // 다음 데이터 요청
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        publisher.subscribe(subscriber);

        // 데이터 방출
        publisher.submit("Flow API");
        publisher.submit("More");

        // 모든 데이터가 처리될 때까지 잠시 대기
        TimeUnit.SECONDS.sleep(2);

        // Publisher 종료
        publisher.close();
    }
}

import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionIssue {
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++)
                    System.out.println("counter : " + counter.incrementAndGet());
            }).start();
        }
    }
}

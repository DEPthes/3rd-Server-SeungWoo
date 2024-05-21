public class VisibilityIssue {
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int count = 0;
            while (running) {
                count++;
            }
            System.out.println("Final count: " + count);
        });
        thread.start();

        Thread.sleep(1000);
        System.out.println("Stopping the thread...");
        running = false;
    }
}

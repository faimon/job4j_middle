package concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        try {
            String[] array = {"\\", "|", "/"};
            String start = "\r load: ";
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    start += array[i];
                    System.out.print(start);
                }
                start = "\r load: ";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    for (int index = 0; index <= 100; index++) {
                        try {
                            Thread.sleep(1000);
                            System.out.print("\rLoading: " + index + " %");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        first.start();
        System.out.println("main method");
    }
}

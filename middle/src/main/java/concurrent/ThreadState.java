package concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {}
        );
        Thread second = new Thread(
                () -> {}
        );
        System.out.println(second.getState());
        System.out.println(first.getState());
        first.start();
        second.start();
        System.out.println(second.getState());
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
        }
        System.out.println(first.getState());
        System.out.println(second.getState());
    }
}

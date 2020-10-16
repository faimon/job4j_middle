package switcher;

public class Switcher {
    public static void main(String[] args) throws InterruptedException {
        MasterSlaveBarrier barrier = new MasterSlaveBarrier();
        Thread first = new Thread(
                () -> {
                    while (true) {
                        try {
                            barrier.tryMaster();
                            System.out.println("Thread A");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        barrier.doneMaster();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (true) {

                        try {
                            barrier.trySlave();
                            System.out.println("Thread B");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        barrier.doneSlave();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
    }
}


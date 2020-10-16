package switcher;

public class MasterSlaveBarrier {
    private boolean status = true;

    public synchronized void tryMaster() throws InterruptedException {
        while (!status) {
            wait();
        }
    }

    public synchronized void trySlave() throws InterruptedException {
        while (status) {
            wait();
        }
    }

    public synchronized void doneMaster() {
        status = false;
        notifyAll();
    }

    public synchronized void doneSlave() {
        status = true;
        notifyAll();
    }
}

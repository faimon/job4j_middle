package wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void offer(T value) {
        synchronized (this) {
            while (queue.size() == maxSize) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            queue.offer(value);
            this.notifyAll();
        }
    }

    public T poll() {
        synchronized (this) {
            try {
                while (queue.isEmpty()) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            T rsl = queue.poll();
            this.notifyAll();
            return rsl;
        }
    }
}

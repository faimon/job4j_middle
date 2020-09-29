package wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public void offer(T value) {
        synchronized (queue) {
            queue.offer(value);
            queue.notifyAll();
        }
    }

    public T poll() {
        synchronized (queue) {
            try {
                if (queue.isEmpty()) {
                    queue.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue.poll();
    }
}

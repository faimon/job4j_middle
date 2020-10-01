package nonblocking;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount<T> {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int current;
        do {
            current = count.get();
        } while (!count.compareAndSet(current, current + 1));
    }

    public int get() {
        return count.get();
    }
}

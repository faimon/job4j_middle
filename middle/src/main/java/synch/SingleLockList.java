package synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private Object[] container = new Object[10];
    private int point = 0;
    private int modCount = 0;

    public synchronized T get(int index) {
        Objects.checkIndex(index, point);
        return (T) container[index];
    }

    public synchronized void add(T value) {
        if (point >= container.length) {
            int newSize = (int) (container.length * 1.5);
            container = Arrays.copyOf(container, newSize);
        }
        container[point++] = value;
        modCount++;
    }

    private Iterator<T> copy(Iterator<T> iterator) {
        List<T> copyArray = new ArrayList<>();
        iterator.forEachRemaining(copyArray::add);
        return copyArray.iterator();
    }

    @Override
    public synchronized Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {
            private int currentPosition = 0;
            private final int modCountIterator = modCount;

            @Override
            public boolean hasNext() {
                if (modCountIterator != modCount) {
                    throw new ConcurrentModificationException();
                }
                return currentPosition < point;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) container[currentPosition++];
            }
        };
        return copy(it);
    }
}

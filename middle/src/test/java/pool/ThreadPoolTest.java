package pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {
    @Test
    public void WhenIncrementInPool() {
        ThreadPool threadPool = new ThreadPool();
        AtomicInteger num = new AtomicInteger();
        Thread first = new Thread(
                num::getAndIncrement
        );
        Thread second = new Thread(
                num::getAndIncrement
        );
        Thread third = new Thread(
                num::getAndIncrement
        );
        threadPool.work(first);
        threadPool.work(second);
        threadPool.work(third);
        threadPool.shutdown();
        assertThat(num.get(), is(3));
    }
}
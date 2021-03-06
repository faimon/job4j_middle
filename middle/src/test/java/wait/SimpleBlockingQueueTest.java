package wait;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void whenOfferAndPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue(5);
        Thread producer = new Thread(
                () -> {
                    queue.offer(1);
                    queue.offer(4);
                    queue.offer(41);
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    assertThat(queue.poll(), is(1));
                    assertThat(queue.poll(), is(4));
                    assertThat(queue.poll(), is(41));
                },
                "Consumer"
        );
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
    }

    @Test
    public void whenTwoThread() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(6);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            queue::offer
                    );
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        buffer.add(queue.poll());
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}
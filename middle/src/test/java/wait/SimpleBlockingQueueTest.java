package wait;

import org.junit.Test;

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
}
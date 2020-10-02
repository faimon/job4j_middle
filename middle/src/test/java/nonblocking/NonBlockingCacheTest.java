package nonblocking;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class NonBlockingCacheTest {
    @Test
    public void whenAdd() {
        NonBlockingCache cache = new NonBlockingCache();
        Base base1 = new Base(1, 0, "task1");
        cache.add(base1);
        assertThat(cache.get(base1.getId()), is(base1));
    }

    @Test
    public void whenUpdate() {
        NonBlockingCache cache = new NonBlockingCache();
        Base base1 = new Base(1, 0, "task1");
        cache.add(base1);
        base1.setDesc("newDesc");
        cache.update(base1);
        assertThat(cache.get(base1.getId()), is(base1));
    }

    @Test
    public void whenRemove() {
        NonBlockingCache cache = new NonBlockingCache();
        Base base1 = new Base(1, 0, "task1");
        cache.add(base1);
        cache.delete(base1);
        assertThat(cache.get(base1.getId()), is(nullValue()));
    }

    @Test
    public void whenTwoThreadUpdate() throws InterruptedException {
        NonBlockingCache cache = new NonBlockingCache();
        Base base1 = new Base(1, 0, "task1");
        Base base2 = new Base(1, 0, "newDesc");
        Base base3 = new Base(2, 0, "task2");
        Base base4 = new Base(2, 0, "new");
        AtomicReference<Exception> ex = new AtomicReference<>();
        cache.add(base1);
        cache.add(base3);
        Thread first = new Thread(
                () -> {
                    try {
                        cache.update(base2);
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    try {
                        cache.update(base4);
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cache.get(1), is(base2));
        assertThat(cache.get(2), is(base4));
    }

    @Test
    public void whenTwoThreadUpdateOneBase() throws InterruptedException {
        NonBlockingCache cache = new NonBlockingCache();
        Base base1 = new Base(1, 0, "task1");
        Base base2 = new Base(1, 0, "newDesc");
        Base base3 = new Base(1, 0, "task2");
        AtomicReference<Exception> ex = new AtomicReference<>();
        cache.add(base1);
        Thread first = new Thread(
                () -> {
                    try {
                        cache.update(base2);
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    try {
                        cache.update(base3);
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(ex.get().getMessage(), is("Error update, different versions of model"));
    }
}
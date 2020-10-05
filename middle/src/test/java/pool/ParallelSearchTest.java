package pool;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParallelSearchTest {
    @Test
    public void whenParallelFindIndex() {
        int[] arr = {100, 7, 5, 3, 1, 2, 4, 11, 22, 111, 5555, 4, 3, 9, 99, 99, 22, 11, 222, 222, 111, 111};
        ForkJoinPool pool = ForkJoinPool.commonPool();
        int index = pool.invoke(new ParallelSearch(arr, 0, arr.length - 1, 100));
        assertThat(index, is(0));
    }

    @Test
    public void whenParallelFindIndex2() {
        int[] arr = {100, 7, 5, 3, 1, 2, 4, 11, 22, 111, 555, 666, 777, 888, 999, 100000, 111};
        ForkJoinPool pool = ForkJoinPool.commonPool();
        int index = pool.invoke(new ParallelSearch(arr, 0, arr.length - 1, 5));
        assertThat(index, is(2));
    }
}
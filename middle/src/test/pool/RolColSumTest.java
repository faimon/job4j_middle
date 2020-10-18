package pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {
    @Test
    public void whenSum() {
        int[][] mass = {new int[]{1, 2, 3}, new int[]{1, 2, 2}};
        RolColSum.Sums[] rsl = RolColSum.sum(mass);
        assertThat(rsl[0].getColSum(), is(2));
        assertThat(rsl[0].getRowSum(), is(6));
        assertThat(rsl[1].getColSum(), is(4));
        assertThat(rsl[1].getRowSum(), is(5));
    }

    @Test
    public void whenCheckSum() {
        int[][] mass = {new int[]{1, 11}, new int[]{1, 44}};
        RolColSum.Sums[] rsl = RolColSum.sum(mass);
        assertThat(rsl[0].getColSum(), is(2));
        assertThat(rsl[0].getRowSum(), is(12));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] mass = {new int[]{1, 2, 3}, new int[]{1, 2, 2}};
        RolColSum.Sums[] rsl = RolColSum.asyncSum(mass);
        assertThat(rsl[0].getColSum(), is(2));
        assertThat(rsl[0].getRowSum(), is(6));
        assertThat(rsl[1].getColSum(), is(4));
        assertThat(rsl[1].getRowSum(), is(5));
    }

    @Test
    public void whenAsyncSum2() throws ExecutionException, InterruptedException {
        int[][] mass = {new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 2}, new int[]{6, 6, 6}};
        RolColSum.Sums[] rsl = RolColSum.asyncSum(mass);
        assertThat(rsl[0].getColSum(), is(8));
        assertThat(rsl[0].getRowSum(), is(15));
        assertThat(rsl[1].getColSum(), is(10));
        assertThat(rsl[1].getRowSum(), is(5));
        assertThat(rsl[2].getColSum(), is(11));
        assertThat(rsl[2].getRowSum(), is(18));
    }
}
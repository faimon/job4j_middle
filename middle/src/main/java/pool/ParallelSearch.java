package pool;

import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {
    private final int[] array;
    private final int from;
    private final int to;
    private final int num;

    public ParallelSearch(int[] array, int from, int to, int num) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.num = num;
    }

    @Override
    protected Integer compute() {
        int currentLength = to - from + 1;
        if (array.length <= 10 || currentLength <= 3) {
            return findIndexElement();
        }
        int mid = (from + to) / 2;
        ParallelSearch leftSearch = new ParallelSearch(array, from, mid, num);
        ParallelSearch rightSearch = new ParallelSearch(array, mid + 1, to, num);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left != -1 ? left : right;
    }

    private Integer findIndexElement() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) {
                return i;
            }
        }
        return -1;
    }
}

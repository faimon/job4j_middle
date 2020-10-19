package pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class RolColSum {
    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] arraySum = new Sums[matrix.length];
        int colSum = 0;
        int rowSum = 0;
        for (int i = 0; i < arraySum.length; i++) {
            // Считаем сумму i строки
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
            }
            // Считаем сумму i столбца
            for (int k = 0; k < matrix.length; k++) {
                colSum += matrix[k][i];
            }
            arraySum[i] = new Sums(rowSum, colSum);
            colSum = 0;
            rowSum = 0;
        }
        return arraySum;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] arraySum = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> map = new HashMap<>();
        for (int i = 0; i < arraySum.length; i++) {
            map.put(i, getAsyncSumForIndex(matrix, i));
        }
        for (int i = 0; i < arraySum.length; i++) {
            arraySum[i] = map.get(i).get();
        }
        return arraySum;
    }

    private static CompletableFuture<Sums> getAsyncSumForIndex(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> new Sums(getRowSum(matrix, index), getColSum(matrix, index)));
    }

    private static int getRowSum(int[][] matrix, int index) {
        int sum = 0;
        for (int i = 0; i < matrix[index].length; i++) {
            sum += matrix[index][i];
        }
        return sum;
    }

    private static int getColSum(int[][] matrix, int index) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][index];
        }
        return sum;
    }

}

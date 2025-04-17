import java.util.Random;

public class ArrClass {
    private final int[] arr;
    private final int threadNum;

    public ArrClass(int dim, int threadNum) {
        this.threadNum = threadNum;
        arr = new int[dim];
        Random rand = new Random();

        for (int i = 0; i < dim; i++) {
            arr[i] = rand.nextInt(10000);
        }

        int randomIndex = rand.nextInt(dim);
        arr[randomIndex] = -rand.nextInt(10000);
    }

    private int minValue = Integer.MAX_VALUE;
    private int minIndex = -1;

    public synchronized void updateMin(int value, int index) {
        if (value < minValue) {
            minValue = value;
            minIndex = index;
        }
    }

    public void findMinWithThreads() {
        ThreadMin[] threads = new ThreadMin[threadNum];
        int chunkSize = arr.length / threadNum;
        int remainder = arr.length % threadNum;

        int start = 0;
        for (int i = 0; i < threadNum; i++) {
            int end = start + chunkSize + (i < remainder ? 1 : 0);
            threads[i] = new ThreadMin(start, end, this);
            threads[i].start();
            start = end;
        }

        for (ThreadMin thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMinIndex() {
        return minIndex;
    }

    public int[] getArr() {
        return arr;
    }
}
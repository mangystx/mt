import java.util.concurrent.Semaphore;

public class Table {
    private final Semaphore[] forks = new Semaphore[5];

    public Table() {
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Semaphore(1);
        }
    }

    public void takeFork(int forkIndex) {
        try {
            forks[forkIndex].acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseFork(int forkIndex) {
        forks[forkIndex].release();
    }
}
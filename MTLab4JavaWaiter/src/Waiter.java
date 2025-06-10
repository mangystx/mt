import java.util.concurrent.Semaphore;

public class Waiter {
    private final Semaphore permission = new Semaphore(4);

    public void requestPermission() {
        try {
            permission.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void releasePermission() {
        permission.release();
    }
}
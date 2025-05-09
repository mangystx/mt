import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class Manager {
    private final Queue<String> buffer = new LinkedList<>();
    private final Semaphore access = new Semaphore(1);
    private final Semaphore empty;
    private final Semaphore full;

    public Manager(int capacity) {
        empty = new Semaphore(0);
        full = new Semaphore(capacity);
    }

    public void produce(String item) throws InterruptedException {
        full.acquire();
        access.acquire();
        buffer.add(item);
        System.out.println(Thread.currentThread().getName() + " produced: " + item);
        access.release();
        empty.release();
    }

    public String consume() throws InterruptedException {
        empty.acquire();
        access.acquire();
        String item = buffer.poll();
        System.out.println(Thread.currentThread().getName() + " consumed: " + item);
        access.release();
        full.release();
        return item;
    }
}
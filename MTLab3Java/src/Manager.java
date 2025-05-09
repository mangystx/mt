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
        System.out.printf("[Виробник %s] додав %s. Буфер: %d\n",
                Thread.currentThread().getName().split("-")[1], item, buffer.size());
        access.release();
        empty.release();
    }

    public String consume() throws InterruptedException {
        empty.acquire();
        access.acquire();
        String item = buffer.poll();
        System.out.printf("[Споживач %s] забрав %s. Буфер: %d\n",
                Thread.currentThread().getName().split("-")[1], item, buffer.size()
        );
        access.release();
        full.release();
        return item;
    }
}
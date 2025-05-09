import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        int bufferSize = 5;
        int totalItems = 20;
        int producers = 2;
        int consumers = 4;

        Manager manager = new Manager(bufferSize);
        AtomicInteger finishedThreads = new AtomicInteger(0); // счётчик завершённых потоков
        int totalThreads = producers + consumers;

        for (int i = 0; i < producers; i++) {
            Thread t = new Thread(() -> {
                Producer producer = new Producer(manager, totalItems / producers);
                producer.run();
                finishedThreads.incrementAndGet();
            }, "Producer-" + i);
            t.start();
        }

        for (int i = 0; i < consumers; i++) {
            Thread t = new Thread(() -> {
                Consumer consumer = new Consumer(manager, totalItems / consumers);
                consumer.run();
                finishedThreads.incrementAndGet();
            }, "Consumer-" + i);
            t.start();
        }

        // Ожидаем завершения всех потоков
        while (finishedThreads.get() < totalThreads) {
            try {
                Thread.sleep(100); // опрашиваем каждые 100мс
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All producers and consumers have completed.");
    }
}

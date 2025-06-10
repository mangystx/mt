public class Philosopher extends Thread {
    private final Table table;
    private final Waiter waiter;
    private final int leftForkIndex, rightForkIndex;
    private final int philosopherId;

    public Philosopher(int philosopherId, Table table, Waiter waiter) {
        this.philosopherId = philosopherId;
        this.table = table;
        this.waiter = waiter;

        rightForkIndex = philosopherId;
        leftForkIndex = (philosopherId + 1) % 5;

        start();
    }

    @Override
    public void run() {
        for (int iteration = 0; iteration < 10; iteration++) {
            System.out.println("Philosopher " + philosopherId + " is thinking (" + (iteration + 1) + " time)");

            waiter.requestPermission();

            table.getFork(rightForkIndex);
            table.getFork(leftForkIndex);

            System.out.println("Philosopher " + philosopherId + " is eating (" + (iteration + 1) + " time)");

            table.putFork(leftForkIndex);
            table.putFork(rightForkIndex);

            waiter.releasePermission();
        }
    }
}
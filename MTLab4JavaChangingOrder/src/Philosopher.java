public class Philosopher extends Thread {
    private final Table table;
    private final int leftForkIndex, rightForkIndex;
    private final int philosopherId;

    public Philosopher(int philosopherId, Table table) {
        this.philosopherId = philosopherId;
        this.table = table;

        if (philosopherId == 0) {
            leftForkIndex = philosopherId;
            rightForkIndex = (philosopherId + 1) % 5;
        } else {
            rightForkIndex = philosopherId;
            leftForkIndex = (philosopherId + 1) % 5;
        }
        start();
    }

    @Override
    public void run() {
        for (int iteration = 0; iteration < 10; iteration++) {
            System.out.println("Philosopher " + philosopherId + " is thinking (" + (iteration + 1) + " time)");

            table.takeFork(rightForkIndex);
            table.takeFork(leftForkIndex);

            System.out.println("Philosopher " + philosopherId + " is eating (" + (iteration + 1) + " time)");

            table.releaseFork(leftForkIndex);
            table.releaseFork(rightForkIndex);
        }
    }
}
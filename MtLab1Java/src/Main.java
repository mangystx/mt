public class Main {

    public static void main(String[] args) {
        int threadCount = 5;

        SequenceThread[] threads = new SequenceThread[threadCount];

        ControllerThread[] controllers = new ControllerThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            int step = i + 1;
            threads[i] = new SequenceThread(i + 1, step);
            controllers[i] = new ControllerThread(threads[i], (i + 1) * 5000);

            threads[i].start();
            controllers[i].start();
        }
    }
}

public class ControllerThread extends Thread {
    private final SequenceThread targetThread;
    private final int delayMillis;

    public ControllerThread(SequenceThread targetThread, int delayMillis) {
        this.targetThread = targetThread;
        this.delayMillis = delayMillis;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        targetThread.allowStop();
    }
}

public class ThreadMin extends Thread {
    private final int start;
    private final int end;
    private final ArrClass arrClass;

    public ThreadMin(int start, int end, ArrClass arrClass) {
        this.start = start;
        this.end = end;
        this.arrClass = arrClass;
    }

    @Override
    public void run() {
        int[] arr = arrClass.getArr();
        int localMin = Integer.MAX_VALUE;
        int localIndex = -1;

        for (int i = start; i < end; i++) {
            if (arr[i] < localMin) {
                localMin = arr[i];
                localIndex = i;
            }
        }

        arrClass.updateMin(localMin, localIndex);
    }
}
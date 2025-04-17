public class Main {
    public static void main(String[] args) {
        int dim = 10_000_000;
        int threadNum = 5;

        ArrClass arrClass = new ArrClass(dim, threadNum);
        arrClass.findMinWithThreads();

        System.out.println("Minimum value: " + arrClass.getMinValue());
        System.out.println("Index of minimum value: " + arrClass.getMinIndex());
    }
}
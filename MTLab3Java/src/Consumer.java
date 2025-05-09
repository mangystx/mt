class Consumer {
    private final Manager manager;
    private final int count;

    public Consumer(Manager manager, int count) {
        this.manager = manager;
        this.count = count;
    }

    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                manager.consume();
                Thread.sleep(150);
            }
            System.out.printf("[Споживач %s] завершив роботу.\n", Thread.currentThread().getName().split("-")[1]);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

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
                String item = manager.consume();
                System.out.println(Thread.currentThread().getName() + " consumed: " + item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

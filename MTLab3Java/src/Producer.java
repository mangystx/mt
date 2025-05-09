class Producer {
    private final Manager manager;
    private final int count;

    public Producer(Manager manager, int count) {
        this.manager = manager;
        this.count = count;
    }

    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                manager.produce("item " + i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

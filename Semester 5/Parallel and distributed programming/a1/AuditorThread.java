public class AuditorThread extends Thread {
    private final Bank bank;
    private final int intervalMillis;
    private volatile boolean running = true;

    public AuditorThread(Bank bank, int intervalMillis) {
        this.bank = bank;
        this.intervalMillis = intervalMillis;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(intervalMillis);

                // temporarily stop transfers
                bank.pauseTransfers();

                System.out.println("\n--- Auditor Paused Transfers ---");
                bank.verifyIntegrity();
                System.out.println("--- Auditor Resuming Transfers ---\n");

                // resume transfers
                bank.resumeTransfers();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopAuditing() {
        running = false;
        this.interrupt();
    }
}

public class TransferThread extends Thread {
    private final Bank bank;
    private final long fromId;
    private final long toId;
    private final long transferValue;
    private final int attempts;
    private int successCount;
    private int failureCount;

    public TransferThread(Bank bank, long fromId, long toId, long transferValue, int attempts) {
        this.bank = bank;
        this.fromId = fromId;
        this.toId = toId;
        this.transferValue = transferValue;
        this.attempts = attempts;
        this.successCount = 0;
        this.failureCount = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < attempts; i++) {
            boolean result = bank.performTransfer(fromId, toId, transferValue);
            if (result)
                successCount++;
            else
                failureCount++;

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }
}

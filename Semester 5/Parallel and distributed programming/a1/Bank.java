import java.util.*;

public class Bank {
    private final Map<Integer, Account> accountMap;
    private final Integer startingTotal;

    // coordination flags
    private boolean paused = false;

    public Bank() {
        this.accountMap = new HashMap<>();
        this.startingTotal = 0;
    }

    public Bank(Map<Integer, Integer> initialData) {
        this.accountMap = new HashMap<>();
        int total = 0;
        for (Map.Entry<Integer, Integer> e : initialData.entrySet()) {
            accountMap.put(e.getKey(), new Account(e.getKey(), e.getValue()));
            total += e.getValue();
        }
        this.startingTotal = total;
    }

    // ---- transfer pause control ----
    public synchronized void pauseTransfers() {
        paused = true;
    }

    public synchronized void resumeTransfers() {
        paused = false;
        notifyAll();
    }

    public synchronized void waitIfPaused() {
        while (paused) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    // --------------------------------

    public void registerAccount(Integer id, Integer balance) {
        synchronized (accountMap) {
            if (!accountMap.containsKey(id)) {
                accountMap.put(id, new Account(id, balance));
            }
        }
    }

    public Account fetchAccount(Integer id) {
        synchronized (accountMap) {
            return accountMap.get(id);
        }
    }

    public boolean performTransfer(Integer fromId, Integer toId, Integer value) {
        if (value <= 0)
            throw new IllegalArgumentException("Transfer amount must be positive");
        if (fromId.equals(toId))
            throw new IllegalArgumentException("Source and destination must differ");

        // wait if paused by auditor
        waitIfPaused();

        Account src = fetchAccount(fromId);
        Account dest = fetchAccount(toId);
        if (src == null || dest == null)
            return false;

        Account firstLock = (fromId < toId) ? src : dest;
        Account secondLock = (fromId < toId) ? dest : src;

        synchronized (firstLock.getLock()) {
            synchronized (secondLock.getLock()) {
                if (src.getBalance() >= value) {
                    src.setBalance(src.getBalance() - value);
                    dest.setBalance(dest.getBalance() + value);
                    return true;
                }
                return false;
            }
        }
    }

    public Integer getOverallBalance() {
        int sum = 0;
        synchronized (accountMap) {
            for (Account a : accountMap.values()) {
                sum += a.getBalance();
            }
        }
        return sum;
    }

    public void verifyIntegrity() {
        int current = getOverallBalance();
        boolean valid = current == startingTotal;

        System.out.printf("Integrity Check: Original=%d, Current=%d%n",
                startingTotal, current);

        if (!valid) {
            throw new AssertionError(
                    String.format("Balance mismatch! Expected %d, got %d",
                            startingTotal, current));
        }
    }

    public void displayBalances() {
        synchronized (accountMap) {
            System.out.println("Accounts Overview:");
            for (Account a : accountMap.values()) {
                System.out.printf("  Account %d: $%d%n", a.getId(), a.getBalance());
            }
        }
    }
}

import java.util.*;

public class Bank {
    private final Map<Long, Account> accountMap;
    private final long startingTotal;

    public Bank() {
        this.accountMap = new HashMap<>();
        this.startingTotal = 0;
    }

    public Bank(Map<Long, Long> initialData) {
        this.accountMap = new HashMap<>();
        long total = 0;

        for (Map.Entry<Long, Long> e : initialData.entrySet()) {
            accountMap.put(e.getKey(), new Account(e.getKey(), e.getValue()));
            total += e.getValue();
        }

        this.startingTotal = total;
    }

    public void registerAccount(long id, long balance) {
        synchronized (accountMap) {
            if (!accountMap.containsKey(id)) {
                accountMap.put(id, new Account(id, balance));
            }
        }
    }

    public Account fetchAccount(long id) {
        synchronized (accountMap) {
            return accountMap.get(id);
        }
    }

    public boolean performTransfer(long fromId, long toId, long value) {
        if (value <= 0)
            throw new IllegalArgumentException("Transfer amount must be positive");
        if (fromId == toId)
            throw new IllegalArgumentException("Source and destination must differ");

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

    public long getOverallBalance() {
        long sum = 0;
        synchronized (accountMap) {
            for (Account a : accountMap.values()) {
                sum += a.getBalance();
            }
        }
        return sum;
    }

    public void verifyIntegrity() {
        long current = getOverallBalance();
        boolean valid = current == startingTotal;

        System.out.printf("Integrity Check: Original=%d, Current=%d%n", startingTotal, current);

        if (!valid) {
            throw new AssertionError(
                    String.format("Balance mismatch! Expected %d, got %d", startingTotal, current));
        }
    }

    public void displayBalances() {
        synchronized (accountMap) {
            System.out.println("Accounts Overview:");
            for (Account a : accountMap.values()) {
                System.out.printf("Account %d: $%d%n", a.getId(), a.getBalance());
            }
        }
    }
}

public class Account {
    private final long id;
    private long balance;
    private final Object lockObj;

    public Account(long id, long startingBalance) {
        this.id = id;
        this.balance = startingBalance;
        this.lockObj = new Object();
    }

    public long getId() {
        return id;
    }

    public long getBalance() {
        synchronized (lockObj) {
            return balance;
        }
    }

    public void setBalance(long newBalance) {
        synchronized (lockObj) {
            this.balance = newBalance;
        }
    }

    public Object getLock() {
        return lockObj;
    }
}

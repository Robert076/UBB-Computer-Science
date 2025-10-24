public class Account {
    private final Integer id;
    private Integer balance;
    private final Object lockObj;

    public Account(Integer id, Integer startingBalance) {
        this.id = id;
        this.balance = startingBalance;
        this.lockObj = new Object();
    }

    public Integer getId() {
        return id;
    }

    public Integer getBalance() {
        synchronized (lockObj) {
            return balance;
        }
    }

    public void setBalance(Integer newBalance) {
        synchronized (lockObj) {
            this.balance = newBalance;
        }
    }

    public Object getLock() {
        return lockObj;
    }
}

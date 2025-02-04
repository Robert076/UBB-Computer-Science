package model.dataStructures.myLockTable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import MyException.MyException;

public class MyLockTable<K, V> implements MyILockTable<K, V> {
    private ConcurrentHashMap<K, V> lockTable;
    private int address = 0;

    public MyLockTable() {
        this.lockTable = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K key) {
        return lockTable.get(key);
    }

    @Override
    public V lookup(K key) throws MyException {
        if (!this.lockTable.containsKey(key)) {
            throw new MyException("Lock table: key looked up is not defined");
        }
        return this.lockTable.get(key);
    }

    @Override
    public void put(K key, V value) {
        this.lockTable.put(key, value);
    }

    @Override
    public int getFreeAddress() {
        this.address++;
        return address;
    }

    @Override
    public boolean isDefined(K key) {
        return this.lockTable.containsKey(key);
    }

    @Override
    public Map<K, V> getContent() {
        return this.lockTable;
    }

    @Override
    public void replace(K key, V value) {
        this.lockTable.replace(key, value);
    }
}

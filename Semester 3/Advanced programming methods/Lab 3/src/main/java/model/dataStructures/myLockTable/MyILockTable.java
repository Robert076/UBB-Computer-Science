package model.dataStructures.myLockTable;

import java.util.Map;

import MyException.MyException;

public interface MyILockTable<K, V> {
    public V get(K key);

    V lookup(K key) throws MyException;

    public void put(K key, V value);

    public void replace(K key, V value);

    int getFreeAddress();

    boolean isDefined(K key);

    public Map<K, V> getContent();
}

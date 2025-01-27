package model.dataStructures.myFileTable;

import MyException.MyException;

public interface MyIFileTable<K, V> {
    boolean isDefined(K key);

    V lookup(K key) throws MyException;

    void put(K key, V value);

    void update(K key, V val) throws MyException;

    void delete(K key) throws MyException;
}

package model.dataStructures.myDictionary;

import MyException.MyException;
import java.util.HashMap;
import java.util.List;

public interface MyIDictionary<K, V> {
    boolean isDefined(K key);

    V lookup(K key) throws MyException;

    void put(K key, V value);

    void update(K key, V val) throws MyException;

    List<V> getValues();

    HashMap<K, V> getContent();

    MyIDictionary<K, V> deepCopy();
}

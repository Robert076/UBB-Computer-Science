package model.dataStructures.myDictionary;

import java.util.Map;
import java.util.HashMap;
import MyException.MyException;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private Map<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<K, V>();
    }

    @Override
    public boolean isDefined(K key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public void put(K key, V value) {
        this.dictionary.put(key, value);
    }

    @Override
    public V lookup(K key) throws MyException {
        if (this.isDefined(key)) {
            return this.dictionary.get(key);
        } else {
            throw new MyException("Key provided for lookup doesn't exist");
        }
    }

    @Override
    public void update(K key, V val) throws MyException {
        if (this.isDefined(key)) {
            this.dictionary.put(key, val);
        } else {
            throw new MyException("Key provided for update doesn't exist");
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (K key : this.dictionary.keySet()) {
            s.append(key).append(" -> ").append(this.dictionary.get(key)).append("\n");
        }
        return s.toString();
    }
}
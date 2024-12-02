package model.dataStructures.myDictionary;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import MyException.MyException;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private HashMap<K, V> dictionary;

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
    public HashMap<K, V> getContent() {
        return this.dictionary;
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
    public List<V> getValues() {
        return new LinkedList<V>(this.dictionary.values());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (K key : this.dictionary.keySet()) {
            s.append(key).append(" -> ").append(this.dictionary.get(key)).append(", ");
        }
        if (s.isEmpty())
            return "Empty\n";
        s.append('\n');
        return s.toString();
    }

}
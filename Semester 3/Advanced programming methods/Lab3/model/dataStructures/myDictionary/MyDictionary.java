package model.dataStructures.myDictionary;

import java.util.Map;
import java.util.HashMap;
import MyException.MyException;

public class MyDictionary<T, V> implements MyIDictionary<T, V> {
    private Map<T, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<T, V>();
    }

    public boolean isDefined(T id) {
        return this.dictionary.containsKey(id);
    }

    public V lookup(T id) throws MyException {
        if (isDefined(id)) {
            return this.dictionary.get(id);
        } else {
            throw new MyException("Key provided for lookup doesn't exist");
        }
    }

    public void update(T id, V val) throws MyException {
        if (isDefined(id)) {
            this.dictionary.put(id, val);
        } else {
            throw new MyException("Key provided for update doesn't exist");
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T key : this.dictionary.keySet()) {
            s.append(key).append(" -> ").append(this.dictionary.get(key)).append("\n");
        }
        return s.toString();
    }
}
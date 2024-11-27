package model.dataStructures.myFileTable;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import MyException.MyException;

public class MyFileTable<K, V> implements MyIFileTable<K, V> {
    private Map<K, V> fileTable;

    public MyFileTable() {
        this.fileTable = new HashMap<K, V>();
    }

    @Override
    public boolean isDefined(K key) {
        return this.fileTable.containsKey(key);
    }

    @Override
    public void put(K key, V value) {
        this.fileTable.put(key, value);
    }

    @Override
    public V lookup(K key) throws MyException {
        if (this.isDefined(key)) {
            return this.fileTable.get(key);
        } else {
            throw new MyException("Key provided for lookup doesn't exist");
        }
    }

    @Override
    public void update(K key, V val) throws MyException {
        if (this.isDefined(key)) {
            this.fileTable.put(key, val);
        } else {
            throw new MyException("Key provided for update doesn't exist");
        }
    }

    @Override
    public void delete(K key) throws MyException {
        if (this.isDefined(key)) {
            this.fileTable.remove(key);
        } else {
            throw new MyException("Cannot delete an element that is not in the table");
        }
    }

    @Override
    public String toString() {
        return this.fileTable.isEmpty()
                ? "Empty\n"
                : this.fileTable.entrySet().stream()
                        .map(entry -> entry.getKey() + " -> " + entry.getValue())
                        .collect(Collectors.joining(", ", "", "\n"));
    }
}
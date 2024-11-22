package model.dataStructures.myHeap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import MyException.MyException;
import model.values.Value;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    Map<K, V> heap;
    Integer firstFreeAddress;

    public MyHeap() {
        this.heap = new HashMap<K, V>();
        this.firstFreeAddress = 1;
    }

    @Override
    public V lookup(K key) throws MyException {
        if (!this.contains(key))
            throw new MyException("Invalid key in accessing heap");
        return this.heap.get(key);
    }

    @Override
    public void put(K key, V value) {
        this.heap.put(key, value);
    }

    @Override
    public void remove(K key) {
        this.heap.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return this.heap.containsKey(key);
    }

    @Override
    public String toString() {
        return this.heap.isEmpty() ? "Empty heap"
                : this.heap.entrySet().stream()
                        .map(entry -> entry.getKey() + " -> " + entry.getValue())
                        .collect(Collectors.joining("\n"));

    }

    @Override
    public Map<K, V> getHeap() {
        return this.heap;
    }

    @Override
    public List<V> getValues() {
        return new LinkedList<V>(this.heap.values());
    }

    @Override
    public void setHeap(Map<K, V> _heap) {
        this.heap = _heap;
    }

    @Override
    public Map<Integer, Value> safeGarbageCollector(Set<Integer> usedAddresses, Map<Integer, Value> heap) {
        Map<Integer, Value> newHeap = heap.entrySet().stream()
                .filter(entry -> usedAddresses.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return newHeap;
    }

    @Override
    public Integer allocate() {
        return this.firstFreeAddress++;
    }
}

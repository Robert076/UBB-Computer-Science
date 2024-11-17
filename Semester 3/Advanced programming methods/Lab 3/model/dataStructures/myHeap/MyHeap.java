package model.dataStructures.myHeap;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<I, V> implements MyIHeap<I, V> {
    Map<I, V> heap;
    Integer freeLocation;

    public MyHeap() {
        this.heap = new HashMap<I, V>();
        this.freeLocation = 1;
    }

    public Map<I, V> getHeap() {
        return this.heap;
    }

}

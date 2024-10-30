package model.dataStructures.myList;

import java.util.List;

public interface MyIList<V> {
    void add(V value);

    List<V> getList();
}
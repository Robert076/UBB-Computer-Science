package model.dataStructures.myList;

import java.util.List;
import java.util.ArrayList;

public class MyList<V> implements MyIList<V> {
    List<V> list;

    public MyList() {
        this.list = new ArrayList<V>();
    }

    public List<V> getList() {
        return this.list;
    }

    public void setList(List<V> _list) {
        this.list = _list;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    @Override
    public void add(V value) {
        this.list.add(value);
    }
}

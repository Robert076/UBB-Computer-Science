package model.dataStructures.myDictionary;

import MyException.MyException;

public interface MyIDictionary<T, V> {
    boolean isDefined(T id);

    V lookup(T id) throws MyException;

    void update(T id, V val) throws MyException;
}

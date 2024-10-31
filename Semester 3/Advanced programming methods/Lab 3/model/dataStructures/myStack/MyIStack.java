package model.dataStructures.myStack;

import MyException.MyException;

public interface MyIStack<T> {
    T pop() throws MyException;

    void push(T v);

    boolean isEmpty();

    String toString();
}
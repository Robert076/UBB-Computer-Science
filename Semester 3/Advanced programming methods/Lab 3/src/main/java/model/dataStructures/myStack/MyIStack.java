package model.dataStructures.myStack;

import java.util.List;

import MyException.MyException;

public interface MyIStack<T> {
    T pop() throws MyException;

    void push(T v);

    boolean isEmpty();

    String toString();

    T peek() throws MyException;

    public List<T> toList();
}
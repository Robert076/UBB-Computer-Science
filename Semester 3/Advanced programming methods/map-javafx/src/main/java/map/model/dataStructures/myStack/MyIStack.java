package map.model.dataStructures.myStack;

import map.MyException.MyException;

public interface MyIStack<T> {
    T pop() throws MyException;

    void push(T v);

    boolean isEmpty();

    String toString();

    T peek() throws MyException;
}
package model.dataStructures.myStack;

import java.util.Stack;
import MyException.MyException;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() throws MyException {
        if (stack.empty())
            throw new MyException("Cannot pop from empty stack");
        return this.stack.pop();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }
}
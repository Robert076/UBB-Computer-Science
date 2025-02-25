package model.dataStructures.myStack;

import java.util.List;
import java.util.Stack;

import MyException.MyException;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public List<T> toList() {
        return this.stack.stream().toList();
    }

    @Override
    public T pop() throws MyException {
        if (stack.empty())
            throw new MyException("Cannot pop from empty stack");
        return this.stack.pop();
    }

    @Override
    public T peek() throws MyException {
        if (stack.empty())
            throw new MyException("Empty stack");
        return stack.peek();
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
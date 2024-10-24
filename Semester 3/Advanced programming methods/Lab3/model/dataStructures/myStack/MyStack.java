package model.dataStructures.myStack;

import java.util.Stack;
import MyException.MyException;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    public T pop() throws MyException {
        if (stack.empty())
            throw new MyException("Cannot pop from empty stack");
        return this.stack.pop();
    }

    public void push(T v) {
        this.stack.push(v);
    }
}
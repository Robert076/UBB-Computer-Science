package model.statements;

import model.dataStructures.myStack.*;
import model.programState.*;
import MyException.MyException;

class CompoundStatement implements IStatement {
    IStatement first;
    IStatement second;

    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return state;
    }
}
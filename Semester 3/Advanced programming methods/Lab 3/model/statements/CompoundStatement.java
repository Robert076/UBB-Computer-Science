package model.statements;

import model.dataStructures.myStack.*;
import model.programState.*;
import MyException.MyException;

public class CompoundStatement implements IStatement {
    IStatement first;
    IStatement second;

    public CompoundStatement(IStatement _first, IStatement _second) {
        this.first = _first;
        this.second = _second;
    }

    @Override
    public String toString() {
        return "CompoundStatement{" + this.first.toString() + ";" + this.second.toString() + "}\n";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return state;
    }
}
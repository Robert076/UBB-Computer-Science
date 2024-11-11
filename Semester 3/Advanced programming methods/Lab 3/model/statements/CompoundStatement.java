package model.statements;

import model.dataStructures.myStack.*;
import model.programState.*;
import MyException.MyException;

public class CompoundStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    /*
     * Constructor
     */
    public CompoundStatement(IStatement _first, IStatement _second) {
        this.first = _first;
        this.second = _second;
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "CompoundStatement{" + this.first.toString() + ";" + this.second.toString() + "}";
    }

    public IStatement getFirst() {
        return this.first;
    }

    public IStatement getSecond() {
        return this.second;
    }

    /*
     * Overriding execute(), the method inherited from implementing the IStatement
     * interface
     * We push both the statements on the stack and return the updated state.
     * (although it works the same if we return null instead of the updated state)
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return state;
    }

    @Override
    public CompoundStatement deepCopy() {
        return new CompoundStatement(this.first, this.second);
    }
}
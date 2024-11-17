package model.statements;

import MyException.MyException;
import model.expressions.Expression;
import model.programState.ProgramState;

public class HeapAllocStatement implements IStatement {
    String varName;
    Expression exp;

    public HeapAllocStatement(String _varName, Expression _exp) {
        this.varName = _varName;
        this.exp = _exp;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }

    public HeapAllocStatement deepCopy() {
        return new HeapAllocStatement(this.varName, this.exp.deepCopy());
    }
}

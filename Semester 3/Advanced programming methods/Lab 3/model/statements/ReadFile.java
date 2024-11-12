package model.statements;

import model.expressions.Expression;
import model.programState.ProgramState;

public class ReadFile implements IStatement {
    Expression exp;
    String varName;

    public ReadFile(Expression _exp, String _varName) {
        this.exp = _exp;
        this.varName = _varName;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return state;
    }

    @Override
    public ReadFile deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }
}

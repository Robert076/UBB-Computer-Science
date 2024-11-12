package model.statements;

import model.expressions.*;
import model.programState.ProgramState;

public class openRFile implements IStatement {
    Expression exp;

    public openRFile(Expression _exp) {
        this.exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) {

        return state;
    }

    @Override
    public openRFile deepCopy() {
        return new openRFile(exp);
    }
}

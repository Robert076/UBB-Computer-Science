package model.statements;

import model.expressions.*;
import model.programState.*;
import MyException.MyException;

class PrintStatement implements IStatement {
    Expression exp;

    public Expression getExp() {
        return this.exp;
    }

    public void setExp(Expression _exp) {
        this.exp = _exp;
    }

    @Override
    public String toString() {
        return "PrintStatement{" + exp.toString() + "}\n";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        return state;
    }

    // todo
}

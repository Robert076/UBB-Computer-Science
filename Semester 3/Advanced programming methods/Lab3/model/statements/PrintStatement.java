package model.statements;

import model.expressions.*;
import model.programState.*;
import MyException.MyException;

class PrintStatement implements IStatement {
    Expression exp;

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws MyException {

        return state;
    }

    // todo
}

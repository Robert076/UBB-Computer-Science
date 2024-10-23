package model.statements;

import model.expressions.*;
import model.programState.*;
import MyException.MyException;

class IfStatement implements IStatement {
    Expression exp;
    IStatement thenS;
    IStatement elseS;
    // ...

    IfStatement(Expression _exp, IStatement _then, IStatement _else) {
        exp = _exp;
        thenS = _then;
        elseS = _else;
    }

    public String toString() {
        return "(IF(" + exp.toString() + ") THEN (" + thenS.toString() + ") ELSE (" + elseS.toString() + "))";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        // todo
        return state;
    }
}
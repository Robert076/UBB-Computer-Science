package model.statements;

import model.programState.*;
import MyException.MyException;

class NopStatement implements IStatement {
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }
}
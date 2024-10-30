package model.statements;

import model.programState.*;
import MyException.MyException;

public class NopStatement implements IStatement {
    public NopStatement() {

    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }
}
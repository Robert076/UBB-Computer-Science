package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import model.programState.*;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException, InvalidOperation;

    @Override
    String toString();

    IStatement deepCopy();
}
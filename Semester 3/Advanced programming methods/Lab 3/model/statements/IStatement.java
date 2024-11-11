package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import model.programState.*;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException, InvalidOperation;

    String toString();

    IStatement deepCopy();
}
package model.statements;

import MyException.MyException;
import model.programState.*;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;

    String toString();
}
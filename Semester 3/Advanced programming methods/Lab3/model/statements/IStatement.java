package model.statements;

import model.programState.*;
import MyException.MyException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;

    String toString();
}
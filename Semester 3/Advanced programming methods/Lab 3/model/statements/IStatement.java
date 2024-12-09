package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.programState.*;
import model.types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException, InvalidOperation;

    @Override
    String toString();

    IStatement deepCopy();

    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
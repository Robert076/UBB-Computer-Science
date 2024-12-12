package map.model.statements;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.programState.*;
import map.model.types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException, InvalidOperation;

    @Override
    String toString();

    IStatement deepCopy();

    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
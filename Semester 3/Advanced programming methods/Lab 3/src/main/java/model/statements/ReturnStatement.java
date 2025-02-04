package model.statements;

import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.programState.ProgramState;
import model.types.Type;

public class ReturnStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getSymbolTable().pop();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "ReturnStmt()";
    }

    @Override
    public ReturnStatement deepCopy() {
        return new ReturnStatement();
    }
}

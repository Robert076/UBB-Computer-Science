package model.statements;

import model.programState.*;
import model.types.*;
import MyException.MyException;

class VarDeclStatement implements IStatement {
    String name;
    Type type;

    // we need to see if its already defined. if its already defiend => throw
    // exception
    // if its not already defined, we declare it and define it.

    public VarDeclStatement(String _name, Type _type) {
        name = _name;
        type = _type;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }
}

package model.statements;

import model.dataStructures.myDictionary.*;
import model.programState.*;
import model.values.*;
import model.types.*;
import MyException.MyException;

public class VarDeclStatement implements IStatement {
    String name;
    Type type;

    public VarDeclStatement(String _name, Type _type) {
        name = _name;
        type = _type;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setType(Type _type) {
        this.type = _type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> dict = state.getSymbolTable();

        if (dict.isDefined(this.name)) {
            throw new MyException("Variable is already defined!");
        } else {
            dict.put(this.name, this.type.defaultValue());
        }

        return null;
    }

    @Override
    public String toString() {
        return "VarDeclStatement{" + "name = '" + this.name + "', type = " + this.type + "}\n";
    }
}

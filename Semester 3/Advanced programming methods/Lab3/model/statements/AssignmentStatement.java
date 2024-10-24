package model.statements;

import MyException.MyException;
import model.dataStructures.myStack.*;
import model.expressions.*;
import model.dataStructures.myDictionary.*;
import model.values.*;
import model.types.*;
import model.programState.*;

public class AssignmentStatement implements IStatement {
    String id;
    Expression exp;

    public String toString() {
        return id + "=" + exp.toString();
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();

        if (symTable.isDefined(id)) {
            Value val = exp.eval(symTable);
            Type typeId = (symTable.lookup(id)).getType();
            if (val.getType().equals(typeId))
                symTable.update(id, val);
            else
                throw new MyException(
                        "Declared type of variable " + id + " and type of the assigned expression do not match");
        } else
            throw new MyException("The used variable " + id + " was not declared before");
        return state;
    }
    // ...
}

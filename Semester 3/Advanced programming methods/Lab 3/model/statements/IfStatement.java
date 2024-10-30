package model.statements;

import model.dataStructures.myDictionary.MyIDictionary;
import model.expressions.*;
import model.programState.*;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;
import MyException.MyException;

public class IfStatement implements IStatement {
    Expression exp;
    IStatement thenS;
    IStatement elseS;
    // ...

    IfStatement(Expression _exp, IStatement _then, IStatement _else) {
        exp = _exp;
        thenS = _then;
        elseS = _else;
    }

    @Override
    public String toString() {
        return "(IF(" + this.exp.toString() + ") THEN (" + this.thenS.toString() + ") ELSE (" + this.elseS.toString()
                + "))\n";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> dict = state.getSymbolTable();
        Value val = exp.eval(dict);

        if (!val.getType().equals(new BoolType())) {
            throw new MyException("Type mismatch");
        }

        BoolValue v = (BoolValue) val;
        if (v.getVal())
            state.getStack().push(thenS);
        else
            state.getStack().push(elseS);
        return state;
    }
}
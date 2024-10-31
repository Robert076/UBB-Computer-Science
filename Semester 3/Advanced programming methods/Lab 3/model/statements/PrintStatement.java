package model.statements;

import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myList.MyIList;
import model.dataStructures.myList.MyList;
import model.dataStructures.myStack.MyIStack;
import model.expressions.*;
import model.programState.*;
import model.values.Value;
import MyException.MyException;

public class PrintStatement implements IStatement {
    Expression exp;

    public PrintStatement(Expression _exp) {
        this.exp = _exp;
    }

    public Expression getExp() {
        return this.exp;
    }

    public void setExp(Expression _exp) {
        this.exp = _exp;
    }

    @Override
    public String toString() {
        return "PrintStatement{" + exp.toString() + "}\n";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();

        out.add(exp.eval(symTable));
        return null;
    }

    // todo
}

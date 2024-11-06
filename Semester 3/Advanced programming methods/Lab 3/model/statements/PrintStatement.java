package model.statements;

import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myList.MyIList;
import model.expressions.*;
import model.programState.*;
import model.values.Value;

import MyException.InvalidOperation;
import MyException.MyException;

public class PrintStatement implements IStatement {
    Expression exp;

    /*
     * Constructor
     */
    public PrintStatement(Expression _exp) {
        this.exp = _exp;
    }

    /*
     * Getter for expression
     */
    public Expression getExp() {
        return this.exp;
    }

    /*
     * Setter for expression
     */
    public void setExp(Expression _exp) {
        this.exp = _exp;
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return " PrintStatement{" + exp.toString() + "} ";
    }

    /*
     * Overriding execute(), the method inherited from implementing the interface
     * IStatement
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException, InvalidOperation {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();

        out.add(exp.eval(symTable));
        return state;
    }
}

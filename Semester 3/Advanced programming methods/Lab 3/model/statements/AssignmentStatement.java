package model.statements;

import model.expressions.*;
import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.*;
import model.dataStructures.myHeap.MyIHeap;
import model.values.*;
import model.types.*;
import model.programState.*;

public class AssignmentStatement implements IStatement {
    String id;
    Expression exp;

    /*
     * Constructor
     */
    public AssignmentStatement(String _id, Expression _exp) {
        this.id = _id;
        this.exp = _exp;
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "AssignmentStatement{" + id + " = " + exp.toString() + "}";
    }

    /*
     * Overriding execute, the method inherited from implementing the interface
     * IStatement
     * We check if the variable we want to assign something to is defined
     * If it is, we evaluate the expression whose result we want to assign to our
     * variable
     * If the type of the result is the one we declared previously we continue and
     * update the variable
     * Otherwise we raise exceptions
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException, InvalidOperation {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (!symTable.isDefined(id)) {
            throw new MyException("The used variable " + id + " was not declared before");
        }

        Value val = exp.eval(symTable, heap);
        Type typeId = (symTable.lookup(id)).getType();

        if (!val.getType().equals(typeId)) {
            throw new MyException(
                    "Declared type of variable " + id + " and type of the assigned expression do not match");
        }

        symTable.update(id, val);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(this.id, this.exp);
    }
}

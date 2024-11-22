package model.statements;

import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.expressions.*;
import model.programState.*;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;
import MyException.InvalidOperation;
import MyException.MyException;

public class IfStatement implements IStatement {
    Expression exp;
    IStatement thenS;
    IStatement elseS;

    /*
     * Constructor
     */
    IfStatement(Expression _exp, IStatement _then, IStatement _else) {
        exp = _exp;
        thenS = _then;
        elseS = _else;
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "IF{" + this.exp.toString() + "} THEN {" + this.thenS.toString() + "} ELSE {" + this.elseS.toString()
                + "}}";
    }

    /*
     * Overriding execute(), the method inherited from the implemented interface
     * IStatement.
     * If the statement in the if is true we push the first branch, otherwise the
     * second branch(else) on the execution stack
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException, InvalidOperation {
        MyIDictionary<String, Value> dict = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(dict, heap);

        if (!val.getType().equals(new BoolType())) {
            throw new MyException("Type mismatch");
        }

        BoolValue v = (BoolValue) val;
        if (v.getVal())
            state.getExeStack().push(thenS);
        else
            state.getExeStack().push(elseS);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }
}
package map.model.statements;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.dataStructures.myHeap.MyIHeap;
import map.model.dataStructures.myList.MyIList;
import map.model.expressions.*;
import map.model.programState.*;
import map.model.types.Type;
import map.model.values.Value;

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
        return "PrintStatement{" + exp.toString() + "}";
    }

    /*
     * Overriding execute(), the method inherited from implementing the interface
     * IStatement
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException, InvalidOperation {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        out.add(exp.eval(symTable, heap));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        this.exp.typecheck(typeEnv);
        return typeEnv;
    }
}

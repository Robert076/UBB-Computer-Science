package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.values.RefValue;
import model.values.Value;

public class HeapAllocStatement implements IStatement {
    String varName;
    Expression exp;

    public HeapAllocStatement(String _varName, Expression _exp) {
        this.varName = _varName;
        this.exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if (!symTable.isDefined(this.varName)) {
            throw new MyException("Variable " + this.varName + " is not defined");
        }

        Value symTableVar = symTable.lookup(this.varName);
        if (!(symTableVar instanceof RefValue)) {
            throw new MyException("Variable " + this.varName + " is not a RefType");
        }

        RefValue refValue = (RefValue) symTableVar;
        Value val;
        try {
            val = this.exp.eval(symTable, heap);
        } catch (InvalidOperation e) {
            throw new MyException(e.getMessage());
        }
        if (!refValue.getLocationType().equals(val.getType())) {
            throw new MyException("The type of the expression does not match the type of the inner");
        }

        Integer nextFreeAddress = heap.allocate();
        symTable.put(varName, new RefValue(nextFreeAddress, val.getType()));
        heap.put(nextFreeAddress, val);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocStatement(this.varName, this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "HeapAlloc(" + this.varName + ", " + this.exp + ")";
    }
}

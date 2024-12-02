package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.values.RefValue;
import model.values.Value;

public class WriteHeapStatement implements IStatement {
    String varName;
    Expression exp;

    public WriteHeapStatement(String _varName, Expression _exp) {
        this.varName = _varName;
        this.exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (!symTable.isDefined(this.varName)) {
            throw new MyException("Undefined variable " + this.varName);
        }

        Value symTableVar = symTable.lookup(this.varName);
        if (!(symTableVar instanceof RefValue)) {
            throw new MyException("Variable " + this.varName + " not a RefType");
        }

        RefValue refValue = (RefValue) symTableVar;
        Integer addr = refValue.getAddr();

        if (!heap.contains(addr)) {
            throw new MyException("The address " + addr + " is not in the heap");
        }

        Value val;
        try {
            val = this.exp.eval(symTable, heap);
        } catch (InvalidOperation e) {
            throw new MyException(e.getMessage());
        }

        if (!refValue.getLocationType().equals(val.getType())) {
            throw new MyException("The type of the expression doesn't match");
        }

        heap.put(addr, val);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(this.varName, this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.varName + ", " + this.exp + ")";
    }
}

package model.expressions;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.values.RefValue;
import model.values.Value;

public class ReadHeapExpression implements Expression {
    Expression exp;

    public ReadHeapExpression(Expression _exp) {
        this.exp = _exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Integer, Value> heap) throws MyException {
        Value val;
        try {
            val = this.exp.eval(symTable, heap);
        } catch (InvalidOperation e) {
            throw new MyException(e.getMessage());
        }

        if (!(val instanceof RefValue)) {
            throw new MyException("Expression doesn't evaluate to RefValue");
        }

        RefValue refValue = (RefValue) val;
        Integer addr = refValue.getAddr();
        if (!heap.contains(addr)) {
            throw new MyException("The address " + addr + " is not in the heap");
        }

        return heap.lookup(addr);
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(this.exp.deepCopy());
    }

    @Override
    public String toString() {
        return "readHeap(" + this.exp + ")";
    }
}

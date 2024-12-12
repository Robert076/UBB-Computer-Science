package map.model.expressions;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.dataStructures.myHeap.MyIHeap;
import map.model.types.RefType;
import map.model.types.Type;
import map.model.values.RefValue;
import map.model.values.Value;

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

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = this.exp.typecheck(typeEnv);
        if (type instanceof RefType rft) {
            return rft.getInner();
        } else {
            throw new MyException("ReadHeap: Argument not a RefType");
        }
    }
}

package map.model.statements;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.dataStructures.myHeap.MyIHeap;
import map.model.expressions.Expression;
import map.model.programState.ProgramState;
import map.model.types.RefType;
import map.model.types.Type;
import map.model.values.RefValue;
import map.model.values.Value;

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

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(this.varName);
        Type typeExp = this.exp.typecheck(typeEnv);
        if (!typeVar.equals(new RefType(typeExp))) {
            throw new MyException("HeapAlloc: RHS and LHS have diff types");
        }
        return typeEnv;
    }
}

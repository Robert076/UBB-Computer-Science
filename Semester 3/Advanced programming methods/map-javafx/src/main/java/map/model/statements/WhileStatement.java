package map.model.statements;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.dataStructures.myHeap.MyIHeap;
import map.model.expressions.Expression;
import map.model.programState.ProgramState;
import map.model.types.BoolType;
import map.model.types.Type;
import map.model.values.BoolValue;
import map.model.values.Value;

public class WhileStatement implements IStatement {
    Expression exp;
    IStatement statement;
    // while (exp) { statement }

    public WhileStatement(Expression _exp, IStatement _statement) {
        this.exp = _exp;
        this.statement = _statement;
    }

    public ProgramState execute(ProgramState state) throws MyException, InvalidOperation {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = this.exp.eval(symTable, heap);

        if (!val.getType().equals(new BoolType())) {
            throw new MyException("Expression does not evaluate to bool value.");
        }

        BoolValue boolValue = (BoolValue) val;
        if (boolValue.getVal()) {
            state.getExeStack().push(this);
            state.getExeStack().push(this.statement);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.exp.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + this.exp.toString() + ") {" + this.statement.toString() + "}";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = this.exp.typecheck(typeEnv);
        if (!expType.equals(new BoolType())) {
            throw new MyException("WhileStmt: Exp doesn't evaluate to bool");
        }
        this.statement.typecheck(typeEnv.deepCopy());
        return typeEnv;

    }
}

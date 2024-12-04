package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;

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
}

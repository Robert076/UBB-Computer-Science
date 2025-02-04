package model.statements;

import java.util.HashMap;
import java.util.Map;

import MyException.MyException;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myStack.MyIStack;
import model.dataStructures.myStack.MyStack;
import model.programState.ProgramState;
import model.types.Type;
import model.values.Value;

public class ForkStatement implements IStatement {
    IStatement statement;

    public ForkStatement(IStatement _statement) {
        this.statement = _statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> forkedStack = new MyStack<>();
        MyIDictionary<String, Value> symTableOriginal = state.getSymbolTable();
        HashMap<String, Value> symTableOriginalContent = symTableOriginal.getContent();
        MyIDictionary<String, Value> symTableClone = new MyDictionary<>();
        for (Map.Entry<String, Value> i : symTableOriginalContent.entrySet()) {
            Map.Entry<String, Value> newEntry = i;
            symTableClone.put(newEntry.getKey(), newEntry.getValue().deepCopy());
        }
        ProgramState forked = new ProgramState(forkedStack, symTableClone, state.getOut(), this.statement,
                state.getFileTable(), state.getHeap(), state.getLockTable());
        return forked;
    }

    @Override
    public String toString() {
        return "ForkStatement(" + this.statement + ")";
    }

    @Override
    public ForkStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        this.statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}

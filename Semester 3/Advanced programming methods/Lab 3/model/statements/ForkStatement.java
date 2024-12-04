package model.statements;

import MyException.MyException;
import java.util.HashMap;
import java.util.Map;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myStack.MyIStack;
import model.dataStructures.myStack.MyStack;
import model.programState.ProgramState;
import model.values.Value;

public class ForkStatement implements IStatement {
    IStatement stmt;

    public ForkStatement(IStatement _stmt) {
        this.stmt = _stmt;
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
        ProgramState forked = new ProgramState(forkedStack, symTableClone, state.getOut(), this.stmt,
                state.getFileTable(), state.getHeap());
        return forked;
    }

    @Override
    public String toString() {
        return "ForkStatement(" + this.stmt + ")";
    }

    @Override
    public ForkStatement deepCopy() {
        return new ForkStatement(this.stmt.deepCopy());
    }
}

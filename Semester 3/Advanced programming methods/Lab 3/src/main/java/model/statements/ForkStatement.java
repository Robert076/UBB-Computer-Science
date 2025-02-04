package model.statements;

import java.io.BufferedReader;
import java.util.Map;

import MyException.MyException;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myFileTable.MyIFileTable;
import model.dataStructures.myHeap.MyIHeap;
import model.dataStructures.myList.MyIList;
import model.dataStructures.myProcedureTable.MyIProcedureTable;
import model.dataStructures.myStack.MyIStack;
import model.dataStructures.myStack.MyStack;
import model.programState.ProgramState;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

public class ForkStatement implements IStatement {
    IStatement statement;

    public ForkStatement(IStatement _statement) {
        this.statement = _statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> exeStack = new MyStack<>();

        MyIStack<MyIDictionary<String, Value>> symTableStack = state.getSymbolTable();
        MyIStack<MyIDictionary<String, Value>> symTableStackCopy = new MyStack<MyIDictionary<String, Value>>();

        for (MyIDictionary<String, Value> symTable : symTableStack.toList()) {
            MyIDictionary<String, Value> symTableCopy = new MyDictionary<String, Value>();
            Map<String, Value> originalSymContent = symTable.getContent();
            for (Map.Entry<String, Value> entry : originalSymContent.entrySet()) {
                Value aux = entry.getValue().deepCopy();
                symTableCopy.put(entry.getKey(), entry.getValue().deepCopy());
            }
            symTableStackCopy.push(symTableCopy);
        }

        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIList<Value> out = state.getOut();
        MyIProcedureTable procTable = state.getProcedureTable();

        ProgramState newPrg = new ProgramState(exeStack, symTableStackCopy, out, this.statement, fileTable, heap,
                procTable);
        return newPrg;
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

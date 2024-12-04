package model.programState;

import MyException.InvalidOperation;
import MyException.MyException;
import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;
import model.dataStructures.myDictionary.*;
import model.dataStructures.myFileTable.MyIFileTable;
import model.dataStructures.myHeap.MyIHeap;
import model.dataStructures.myList.*;
import model.dataStructures.myStack.*;
import model.statements.*;
import model.values.*;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> out;
    private MyIFileTable<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;
    private final Integer id;
    private static final AtomicInteger idInc = new AtomicInteger(0);

    IStatement originalProgram; // optional but good

    public ProgramState(MyIStack<IStatement> _exeStack, MyIDictionary<String, Value> _symbolTable, MyIList<Value> _out,
            IStatement _originalProgram, MyIFileTable<StringValue, BufferedReader> _fileTable,
            MyIHeap<Integer, Value> _heap) {
        this.exeStack = _exeStack;
        this.symbolTable = _symbolTable;
        this.out = _out;
        this.originalProgram = deepCopy(_originalProgram); // recreate the entire original prg
        this.fileTable = _fileTable;
        this.heap = _heap;
        this.exeStack.push(_originalProgram);
        this.id = idInc.getAndIncrement();
    }

    public Boolean isNotCompleted() {
        return !(this.exeStack.isEmpty());
    }

    public ProgramState oneStepExecution() throws MyException, InvalidOperation {
        if (this.exeStack.isEmpty()) {
            throw new MyException("Execution stack is empty!");
        }
        IStatement currentStatement = this.exeStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return "+ - - - - - - - - PROGRAM STATE - - - - - - - - +\n\n" +
                " ID = " + this.id +
                " exeStack = " + this.exeStack +
                "\n\n symTable = " + this.symbolTable +
                "\n out = " + out + "\n\n+ - - - - - - - - - - - - - - - - - - - - - - - +\n\n";
    }

    public String toStringLog() throws MyException {
        // Logger for program state - assuming a single statement (entire program)
        StringBuilder logBuilder = new StringBuilder();

        logBuilder.append("\n+ - - - - - - - - PROGRAM STATE - - - - - - - - +\n\n");
        logBuilder.append("ID: ").append(id).append("\n");
        logBuilder.append("Execution Stack:\n");
        if (!exeStack.isEmpty()) {
            IStatement statement = exeStack.peek();

            while (statement instanceof CompoundStatement) {
                CompoundStatement compound = (CompoundStatement) statement;
                logBuilder.append(" ").append(compound.getFirst().toString()).append("\n");
                statement = compound.getSecond();
            }
            logBuilder.append(" ").append(statement.toString()).append("\n");
        }

        // Log symbol table and output
        logBuilder.append("Symbol Table:\n").append(this.symbolTable).append("\n");
        logBuilder.append("Output:\n").append(this.out).append("\n");
        logBuilder.append("FileTable:\n").append(this.fileTable).append("\n");
        logBuilder.append("Heap:\n").append(this.heap).append("\n");

        logBuilder.append("+ - - - - - - - - - - - - - - - - - - - - - - - +\n\n");
        return logBuilder.toString();
    }

    public MyIStack<IStatement> getExeStack() {
        return this.exeStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public MyIList<Value> getOut() {
        return this.out;
    }

    public MyIFileTable<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyIHeap<Integer, Value> getHeap() {
        return this.heap;
    }

    public void setExeStack(MyIStack<IStatement> _exeStack) {
        this.exeStack = _exeStack;
    }

    public void setSymbolTable(MyIDictionary<String, Value> _symbolTable) {
        this.symbolTable = _symbolTable;
    }

    public void setOut(MyIList<Value> _out) {
        this.out = _out;
    }

    public IStatement deepCopy(IStatement stmt) {
        return stmt; // todo
    }

    public void init() {
        // to do
    }
}

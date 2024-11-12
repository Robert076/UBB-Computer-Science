package model.programState;

import model.dataStructures.myStack.*;

import java.io.BufferedReader;

import model.dataStructures.myDictionary.*;
import model.dataStructures.myList.*;
import model.values.*;
import model.statements.*;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, Value> symbolTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;

    IStatement originalProgram; // optional but good

    public ProgramState(MyIStack<IStatement> _exeStack, MyIDictionary<String, Value> _symbolTable, MyIList<Value> _out,
            IStatement _originalProgram, MyIDictionary<StringValue, BufferedReader> _fileTable) {
        this.exeStack = _exeStack;
        this.symbolTable = _symbolTable;
        this.out = _out;
        this.originalProgram = deepCopy(_originalProgram); // recreate the entire original prg
        this.fileTable = _fileTable;
        this.exeStack.push(_originalProgram);
    }

    @Override
    public String toString() {
        return "+ - - - - - - - - PROGRAM STATE - - - - - - - - +\n\n" +
                " exeStack = " + this.exeStack +
                "\n\n symTable = " + this.symbolTable +
                "\n out = " + out + "\n\n+ - - - - - - - - - - - - - - - - - - - - - - - +\n\n";
    }

    // public String toStringLog() throws MyException {
    // // Logger for program state - assuming a single statement (entire program)
    // StringBuilder logBuilder = new StringBuilder();

    // logBuilder.append("\n+ - - - - - - - - PROGRAM STATE - - - - - - - - +\n\n");

    // // Display Execution Stack
    // logBuilder.append("Execution Stack:\n");
    // IStatement statement = exeStack.peek();

    // // Unroll compound statements and add each to logBuilder
    // while (statement instanceof CompoundStatement) {
    // CompoundStatement compound = (CompoundStatement) statement;
    // logBuilder.append(" ").append(compound.getFirst().toString()).append("\n");
    // statement = compound.getSecond();
    // }
    // // Log the last non-compound statement
    // logBuilder.append(" ").append(statement.toString()).append("\n");

    // // Log symbol table and output
    // logBuilder.append("Symbol Table:\n").append(this.symbolTable).append("\n");
    // logBuilder.append("Output:\n").append(this.out).append("\n");

    // logBuilder.append("+ - - - - - - - - - - - - - - - - - - - - - - - +\n\n");
    // return logBuilder.toString();
    // }

    public MyIStack<IStatement> getExeStack() {
        return this.exeStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public MyIList<Value> getOut() {
        return this.out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
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

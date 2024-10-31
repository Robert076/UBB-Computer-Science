package model.programState;

import model.dataStructures.myStack.*;
import model.dataStructures.myDictionary.*;
import model.dataStructures.myList.*;
import model.values.*;
import model.statements.*;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, Value> symbolTable;
    MyIList<Value> out;

    IStatement originalProgram; // optional but good

    public ProgramState(MyIStack<IStatement> _exeStack, MyIDictionary<String, Value> _symbolTable, MyIList<Value> _out,
            IStatement _originalProgram) {
        exeStack = _exeStack;
        symbolTable = _symbolTable;
        out = _out;
        originalProgram = deepCopy(_originalProgram); // recreate the entire original prg
        this.exeStack.push(_originalProgram);
    }

    @Override
    public String toString() {
        return "Program State: \n\n" +
                "exeStack = " + this.exeStack +
                "\nsymTable = " + this.symbolTable +
                "\nout = " + out + "\n\n";
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

    public void setExeStack(MyIStack<IStatement> _exeStack) {
        this.exeStack = _exeStack;
    }

    public void setSymbolTable(MyIDictionary<String, Value> _symbolTable) {
        this.symbolTable = _symbolTable;
    }

    public void setOut(MyIList<Value> _out) {
        this.out = _out;
    }

    IStatement deepCopy(IStatement stmt) {
        return stmt;
    }
}

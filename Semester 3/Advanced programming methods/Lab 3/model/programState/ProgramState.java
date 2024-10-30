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

    ProgramState(MyIStack<IStatement> _exeStack, MyIDictionary<String, Value> _symbolTable, MyIList<Value> _out,
            IStatement _originalProgram) {
        exeStack = _exeStack;
        symbolTable = _symbolTable;
        out = _out;
        originalProgram = deepCopy(_originalProgram); // recreate the entire original prg
        _exeStack.push(_originalProgram);
    }
    // ... override tostring, setters getters for all fields

    public MyIStack<IStatement> getStack() {
        return this.exeStack;
    }

    public MyIDictionary<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    IStatement deepCopy(IStatement stmt) {
        return stmt;
    }
}

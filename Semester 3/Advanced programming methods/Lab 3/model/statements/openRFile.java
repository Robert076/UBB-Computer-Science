package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.expressions.*;
import model.programState.ProgramState;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class OpenRFile implements IStatement {
    Expression exp;

    public OpenRFile(Expression _exp) {
        this.exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, InvalidOperation {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTable, heap);

        if (!val.getType().equals(new StringType())) {
            throw new MyException("OpenRFile must be given a string!");
        }
        StringValue stringValue = (StringValue) val;
        if (state.getFileTable().isDefined(stringValue)) {
            throw new MyException("File already opened");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(stringValue.getVal()));
            state.getFileTable().put(stringValue, br);
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile{" + exp.toString() + "}";
    }
}

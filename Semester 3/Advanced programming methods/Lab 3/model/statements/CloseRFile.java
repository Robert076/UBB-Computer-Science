package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import java.io.BufferedReader;
import java.io.IOException;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

public class CloseRFile implements IStatement {
    Expression exp;

    public CloseRFile(Expression _exp) {
        this.exp = _exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value val;
        try {
            val = this.exp.eval(state.getSymbolTable(), state.getHeap());
        } catch (MyException | InvalidOperation e) {
            throw new MyException(e.getMessage());
        }

        if (!val.getType().equals(new StringType())) {
            throw new MyException("Expression is not a string");
        }
        StringValue stringValue = (StringValue) val;
        BufferedReader br;

        br = state.getFileTable().lookup(stringValue);

        if (br == null) {
            throw new MyException("File " + stringValue.getVal() + " is not opened");
        }

        try {
            br.close();
        } catch (IOException e) {
            throw new MyException("Error closing file: " + e.getMessage());
        }

        state.getFileTable().delete(stringValue);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFile(this.exp.deepCopy());
    }
}

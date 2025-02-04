package model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.StringType;
import model.types.Type;
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
            val = this.exp.eval(state.getSymbolTableTop(), state.getHeap());
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

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = this.exp.typecheck(typeEnv);
        if (!typeExp.equals(new StringType())) {
            throw new MyException("OpenRFileStmt: expression must evaluate to a string");
        }
        return typeEnv;
    }
}

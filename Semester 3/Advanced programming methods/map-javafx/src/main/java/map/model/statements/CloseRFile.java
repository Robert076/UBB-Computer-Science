package map.model.statements;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import java.io.BufferedReader;
import java.io.IOException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.expressions.Expression;
import map.model.programState.ProgramState;
import map.model.types.StringType;
import map.model.types.Type;
import map.model.values.StringValue;
import map.model.values.Value;

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

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = this.exp.typecheck(typeEnv);
        if (!typeExp.equals(new StringType())) {
            throw new MyException("OpenRFileStmt: expression must evaluate to a string");
        }
        return typeEnv;
    }
}

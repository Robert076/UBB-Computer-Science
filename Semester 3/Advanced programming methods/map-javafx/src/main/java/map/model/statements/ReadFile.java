package map.model.statements;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import java.io.BufferedReader;
import java.io.IOException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.expressions.Expression;
import map.model.programState.ProgramState;
import map.model.types.IntType;
import map.model.types.StringType;
import map.model.types.Type;
import map.model.values.IntValue;
import map.model.values.StringValue;
import map.model.values.Value;

public class ReadFile implements IStatement {
    Expression exp;
    String varName;

    public ReadFile(Expression _exp, String _varName) {
        this.exp = _exp;
        this.varName = _varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!state.getSymbolTable().isDefined(this.varName)) {
            throw new MyException("Variable " + this.varName + " is not defined");
        }
        Value val;
        val = state.getSymbolTable().lookup(this.varName);
        if (!val.getType().equals(new IntType())) {
            throw new MyException("Variable " + varName + " is not of type integer");
        }
        Value fileNameVal;
        try {
            fileNameVal = this.exp.eval(state.getSymbolTable(), state.getHeap());
        } catch (InvalidOperation e) {
            throw new MyException(e.getMessage());
        }

        if (!fileNameVal.getType().equals(new StringType())) {
            throw new MyException("File name must be a string");
        }

        StringValue fileName = (StringValue) fileNameVal;
        BufferedReader br;

        br = state.getFileTable().lookup(fileName);
        if (br == null) {
            throw new MyException("File " + fileName.getVal() + " is not opened");
        }

        try {
            String line = br.readLine();
            IntValue value;
            if (line == null) {
                value = new IntValue(0);
            } else {
                try {
                    value = new IntValue(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    throw new MyException("Invalid integer format in file");
                }
            }
            state.getSymbolTable().update(this.varName, value);
        } catch (IOException e) {
            throw new MyException("Error reading from file " + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "ReadFile{" + this.exp.toString() + ", " + this.varName + "}";
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(this.varName);
        Type typeExp = this.exp.typecheck(typeEnv);

        if (!typeExp.equals(new StringType())) {
            throw new MyException("ReadFileStmt: expression must be a string");
        }
        if (!typeVar.equals(new IntType())) {
            throw new MyException("ReadFileStmt: variable type must be integer");
        }
        return typeEnv;
    }
}
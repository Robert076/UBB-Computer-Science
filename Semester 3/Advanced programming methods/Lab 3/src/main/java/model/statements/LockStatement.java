package model.statements;

import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myLockTable.MyILockTable;
import model.programState.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class LockStatement implements IStatement {
    String variable;

    public LockStatement(String _variable) {
        this.variable = _variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyILockTable<Integer, Integer> lockTable = state.getLockTable();

        if (!symTable.isDefined(this.variable)) {
            throw new MyException("LockStmt: variable is not declared");
        }
        if (!symTable.lookup(this.variable).getType().equals(new IntType())) {
            throw new MyException("LockStmt: variable is not of integer type");
        }

        int foundIndex = ((IntValue) symTable.lookup(this.variable)).getVal();

        if (lockTable.lookup(foundIndex) == -1) {
            lockTable.replace(foundIndex, state.getId());

        } else {
            state.getExeStack().push(new LockStatement(this.variable));
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type variableType = typeEnv.lookup(this.variable);
        if (!variableType.equals(new IntType())) {
            throw new MyException("LockStmt: variable is not of type int(in typecheck)");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "LockStmt(" + this.variable + ")";
    }

    @Override
    public LockStatement deepCopy() {
        return new LockStatement(this.variable);
    }
}

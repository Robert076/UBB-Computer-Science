package model.statements;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myLockTable.MyILockTable;
import model.programState.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class UnlockStatement implements IStatement {
    String variable;

    public UnlockStatement(String _variable) {
        this.variable = _variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Lock lock = new ReentrantLock();
        lock.lock();

        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyILockTable<Integer, Integer> lockTable = state.getLockTable();

        if (!symTable.isDefined(this.variable)) {
            throw new MyException("UnlockStmt: var is not declared in sym table");
        }
        if (!symTable.lookup(this.variable).getType().equals(new IntType())) {
            throw new MyException("UnlockStmt: variable not of integer type");
        }
        int foundIndex = ((IntValue) symTable.lookup(this.variable)).getVal();

        if (lockTable.lookup(foundIndex).equals(state.getId())) {
            lockTable.replace(foundIndex, -1);
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type variableType = typeEnv.lookup(variable);

        if (!variableType.equals(new IntType())) {
            throw new MyException("UnlockStmt: var is not int (typecheck)");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "UnlockStatement(" + this.variable + ")";
    }

    @Override
    public UnlockStatement deepCopy() {
        return new UnlockStatement(this.variable);
    }
}

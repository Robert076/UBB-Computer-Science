package model.statements;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.programState.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

public class NewLockStatement implements IStatement {
    String variable;

    public NewLockStatement(String _variable) {
        this.variable = _variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Lock lock = new ReentrantLock();
        lock.lock();
        int getFreeAddress = state.getLockTable().getFreeAddress();
        state.getLockTable().put(getFreeAddress, -1);
        if (!state.getSymbolTable().isDefined(this.variable)) {
            throw new MyException("NewLockStmt: variable is not defined in symtable");
        }
        Type variableType = state.getSymbolTable().lookup(this.variable).getType();

        if (!variableType.equals(new IntType())) {
            throw new MyException("NewLockStmt: variable is not of type int");
        }

        state.getSymbolTable().update(this.variable, new IntValue(getFreeAddress));
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type variableType = typeEnv.lookup(this.variable);

        if (!variableType.equals(new IntType())) {
            throw new MyException("NewLockStmt: Variable not of type int");
        }

        return typeEnv;
    }

    @Override
    public String toString() {
        return "newLock(" + this.variable + ")";
    }

    @Override
    public NewLockStatement deepCopy() {
        return new NewLockStatement(this.variable);
    }
}
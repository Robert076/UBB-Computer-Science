package model.statements;

import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myStack.MyIStack;
import model.dataStructures.myStack.MyStack;
import model.programState.ProgramState;
import model.types.Type;

public class ForkStatement implements IStatement {
    IStatement statement;

    public ForkStatement(IStatement _statement) {
        this.statement = _statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> newThreadStack = new MyStack<>();
        newThreadStack.push(this.statement);
        return new ProgramState(newThreadStack, state.getSymbolTable(), state.getOut(), state.getOriginalProgram(),
                state.getFileTable(), state.getHeap(), state.getProcedureTable());
    }

    @Override
    public String toString() {
        return "ForkStatement(" + this.statement + ")";
    }

    @Override
    public ForkStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        this.statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}

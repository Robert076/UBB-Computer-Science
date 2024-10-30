package controller;

import MyException.MyException;
import model.dataStructures.myStack.MyIStack;
import model.programState.ProgramState;
import model.statements.IStatement;
import repository.*;

public class Controller {
    IRepository repo;

    public Controller(IRepository _repo) {
        this.repo = _repo;
    }

    public ProgramState oneStepExecution(ProgramState state) throws MyException {
        MyIStack<IStatement> stk = state.getStack();
        if (stk.isEmpty()) {
            throw new MyException("Execution stack is empty!");
        }
        IStatement currentStatement = stk.pop();
        return currentStatement.execute(state);
    }

    public void fullExecution() throws MyException {
        ProgramState prgState = this.repo.getCurrentProgram();
        while (!prgState.getStack().isEmpty()) {
            this.oneStepExecution(prgState);
        }
    }
}

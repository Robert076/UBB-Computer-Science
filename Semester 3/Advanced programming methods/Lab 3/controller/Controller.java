package controller;

import MyException.InvalidOperation;
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

    public ProgramState oneStepExecution(ProgramState state) throws MyException, InvalidOperation {
        MyIStack<IStatement> stk = state.getExeStack();
        if (stk.isEmpty()) {
            throw new MyException("Execution stack is empty!");
        }
        IStatement currentStatement = stk.pop();
        return currentStatement.execute(state);
    }

    public void fullExecution(int flag) throws MyException, InvalidOperation {
        ProgramState prgState = this.repo.getCurrentProgram();
        while (!prgState.getExeStack().isEmpty()) {
            if (flag == 1)
                System.out.println(prgState);
            this.oneStepExecution(prgState);
        }
    }

    public void setCurrentProgram(ProgramState programState) {
        this.repo.setCurrentProgram(programState);
    }
}

package controller;

import java.io.IOException;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myStack.MyIStack;
import model.programState.ProgramState;
import model.statements.IStatement;
import repository.*;

public class Controller {
    IRepository repo;
    Boolean displayFlag;

    public Controller(IRepository _repo) {
        this.repo = _repo;
        this.displayFlag = false;
    }

    public ProgramState oneStepExecution(ProgramState state) throws MyException, InvalidOperation {
        MyIStack<IStatement> stk = state.getExeStack();
        if (stk.isEmpty()) {
            throw new MyException("Execution stack is empty!");
        }
        IStatement currentStatement = stk.pop();
        return currentStatement.execute(state);
    }

    public void fullExecution() throws MyException, InvalidOperation, IOException {
        ProgramState prgState = this.repo.getCurrentProgram();
        this.repo.logProgramStateExecution();
        while (!prgState.getExeStack().isEmpty()) {
            if (this.displayFlag == true)
                System.out.println(prgState);
            this.oneStepExecution(prgState);
            this.repo.logProgramStateExecution();
        }
        if (this.displayFlag == true)
            System.out.println(prgState);

    }

    public void setCurrentProgram(ProgramState programState) {
        this.repo.setCurrentProgram(programState);
    }

    public void setDisplayFlag(Boolean _displayFlag) {
        this.displayFlag = _displayFlag;
    }

    public Boolean getDisplayFlag() {
        return this.displayFlag;
    }

    public void setLogFile(String logFile) {
        this.repo.setLogFile(logFile);
    }
}

package repository;

import java.io.IOException;

import MyException.MyException;
import model.programState.ProgramState;

public interface IRepository {
    void setCurrentProgram(ProgramState currentProgram);

    ProgramState getCurrentProgram();

    void logProgramStateExecution() throws MyException, IOException;

    void setLogFile(String _logFile);
}

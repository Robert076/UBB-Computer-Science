package repository;

import model.programState.ProgramState;

public interface IRepository {
    void setCurrentProgram(ProgramState currentProgram);

    ProgramState getCurrentProgram();
}

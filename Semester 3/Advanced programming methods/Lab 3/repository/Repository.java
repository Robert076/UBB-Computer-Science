package repository;

import model.programState.ProgramState;

public class Repository implements IRepository {
    ProgramState programState;

    public ProgramState getCurrentProgram() {
        return this.programState;
    }
}

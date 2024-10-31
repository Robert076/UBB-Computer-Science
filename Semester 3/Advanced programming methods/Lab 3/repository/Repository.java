package repository;

import model.programState.ProgramState;

public class Repository implements IRepository {
    ProgramState programState;

    public void setCurrentProgram(ProgramState _programState) {
        this.programState = _programState;
    }

    public ProgramState getCurrentProgram() {
        return this.programState;
    }
}

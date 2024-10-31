package repository;

import model.programState.ProgramState;

public class Repository implements IRepository {
    /*
     * The program state (it's an abstract syntax tree)
     */
    private ProgramState programState;

    /*
     * Setter for program state
     */
    public void setCurrentProgram(ProgramState _programState) {
        this.programState = _programState;
    }

    /*
     * Getter for program state
     */
    public ProgramState getCurrentProgram() {
        return this.programState;
    }
}

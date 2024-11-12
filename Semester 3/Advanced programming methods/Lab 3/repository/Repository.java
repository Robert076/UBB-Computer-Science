package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import MyException.MyException;
import model.programState.ProgramState;

public class Repository implements IRepository {
    String logFilePath;
    /*
     * The program state (it's an abstract syntax tree)
     */
    private ProgramState programState;

    public Repository(ProgramState _programState, String _logfilePath) {
        this.programState = _programState;
        this.logFilePath = _logfilePath;
    }

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

    /*
     * Logging all states of the program in a file
     */
    @Override
    public void logProgramStateExecution() throws MyException, IOException {
        if (this.logFilePath == null) {
            throw new MyException("You must provide a path for the log file!");
        }
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        } catch (IOException e) {
            throw new MyException("Error opening log file!");
        }
        logFile.println(this.programState.toString());
        logFile.close();
    }

    /*
     * Pay attention to the fact that the path starts from the root folder and not
     * from the folder we are in
     */
    public void setLogFile(String _logFilePath) {
        this.logFilePath = _logFilePath;
    }

}

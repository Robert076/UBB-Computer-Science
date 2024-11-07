package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import MyException.MyException;
import model.programState.ProgramState;

public class Repository implements IRepository {
    String logFile;
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

    /*
     * Logging all states of the program in a file
     */
    @Override
    public void logProgramStateExecution() throws MyException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFile)));
        logFile.write(dtf.format(now) + "\n");
        logFile.write(programState.toString());
        logFile.close();
    }

    /*
     * Pay attention to the fact that the path starts from the root folder and not
     * from the folder we are in
     */
    public void setLogFile(String _logFile) {
        this.logFile = _logFile;
    }

}

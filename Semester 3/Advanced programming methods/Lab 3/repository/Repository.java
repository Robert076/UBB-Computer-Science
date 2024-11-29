package repository;

import MyException.MyException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.programState.ProgramState;

public class Repository implements IRepository {
    private String logFilePath;
    private List<ProgramState> programStates;

    public Repository(String _logfilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = _logfilePath;
    }

    @Override
    public void setPrgList(List<ProgramState> prgList) {
        this.programStates = prgList;
    }

    @Override
    public List<ProgramState> getPrgList() {
        return this.programStates;
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws MyException, IOException {
        if (this.logFilePath == null) {
            throw new MyException("You must provide a path for the log file!");
        }
        PrintWriter logFile;
        try {
            Boolean appendValue = true;
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, appendValue)));
        } catch (IOException e) {
            throw new MyException("Error opening log file!");
        }
        logFile.println(state.toStringLog());
        logFile.close();
    }

    /*
     * Pay attention to the fact that the path starts from the root folder and not
     * from the folder we are in
     */
    @Override
    public void setLogFile(String _logFilePath) {
        this.logFilePath = _logFilePath;
    }

}

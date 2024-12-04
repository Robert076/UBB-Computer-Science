package repository;

import MyException.MyException;
import java.io.IOException;
import java.util.List;
import model.programState.ProgramState;

public interface IRepository {
    void setPrgList(List<ProgramState> prgList);

    List<ProgramState> getPrgList();

    void logProgramStateExecution(ProgramState state) throws MyException, IOException;

    void setLogFile(String _logFile);
}

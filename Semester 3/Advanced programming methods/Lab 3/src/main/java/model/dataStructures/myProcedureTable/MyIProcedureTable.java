package model.dataStructures.myProcedureTable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import MyException.MyException;
import javafx.util.Pair;
import model.statements.IStatement;

public interface MyIProcedureTable {
    boolean isDefined(String key);

    javafx.util.Pair<List<String>, IStatement> get(String key) throws MyException;

    void update(String key, javafx.util.Pair<List<String>, IStatement> value);

    Collection<javafx.util.Pair<List<String>, IStatement>> values();

    void remove(String key) throws MyException;

    Map<String, Pair<List<String>, IStatement>> getContent();

    MyIProcedureTable deepCopy() throws MyException;

    Set<Map.Entry<String, javafx.util.Pair<List<String>, IStatement>>> entrySet();
}

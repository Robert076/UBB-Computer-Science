package model.dataStructures.myProcedureTable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import MyException.MyException;
import javafx.util.Pair;
import model.statements.IStatement;

public class MyProcedureTable implements MyIProcedureTable {
    Map<String, javafx.util.Pair<List<String>, IStatement>> procedureTable;

    public MyProcedureTable() {
        this.procedureTable = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isDefined(String key) {
        return procedureTable.containsKey(key);
    }

    @Override
    public javafx.util.Pair<List<String>, IStatement> get(String key) throws MyException {
        if (!isDefined(key)) {
            throw new MyException("Key undefined!");
        }
        return this.procedureTable.get(key);
    }

    @Override
    public Set<Map.Entry<String, javafx.util.Pair<List<String>, IStatement>>> entrySet() {
        return this.procedureTable.entrySet();
    }

    @Override
    public void update(String key, javafx.util.Pair<List<String>, IStatement> value) {
        procedureTable.put(key, value);
    }

    @Override
    public Collection<javafx.util.Pair<List<String>, IStatement>> values() {
        return this.procedureTable.values();
    }

    @Override
    public void remove(String key) throws MyException {
        if (!isDefined(key)) {
            throw new MyException("Key undefined!");
        }
        this.procedureTable.remove(key);
    }

    @Override
    public Map<String, Pair<List<String>, IStatement>> getContent() {
        return this.procedureTable;
    }

    @Override
    public MyIProcedureTable deepCopy() throws MyException {
        MyIProcedureTable newProcTable = new MyProcedureTable();

        for (String key : this.procedureTable.keySet())
            newProcTable.update(key, this.procedureTable.get(key));

        return newProcTable;
    }

}

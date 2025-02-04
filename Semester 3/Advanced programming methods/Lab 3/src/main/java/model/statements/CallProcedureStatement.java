package model.statements;

import java.util.List;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.dataStructures.myProcedureTable.MyIProcedureTable;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.Type;
import model.values.Value;

public class CallProcedureStatement implements IStatement {
    private final String procedureName;
    private final List<Expression> parameters;

    public CallProcedureStatement(String _procedureName, List<Expression> _parameters) {
        this.procedureName = _procedureName;
        this.parameters = _parameters;
    }

    @Override
    public String toString() {
        return "CallProcedureStmt " + this.procedureName + "(" + this.parameters.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTableTop();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIProcedureTable procTable = state.getProcedureTable();

        if (!procTable.isDefined(this.procedureName)) {
            throw new MyException("Call: procedure not defined!");
        }

        List<String> procedureParameters = procTable.get(this.procedureName).getKey();
        IStatement procedureBody = procTable.get(this.procedureName).getValue();
        MyIDictionary<String, Value> newSymbolTable = new MyDictionary<>();
        for (String parameter : procedureParameters) {
            int index = procedureParameters.indexOf(parameter);
            try {
                newSymbolTable.put(parameter, parameters.get(index).eval(symbolTable, heap));
            } catch (InvalidOperation e) {
                throw new MyException(e.getMessage());
            }
        }
        state.getSymbolTable().push(newSymbolTable);
        state.getExeStack().push(new ReturnStatement());
        state.getExeStack().push(procedureBody);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public CallProcedureStatement deepCopy() {
        return new CallProcedureStatement(procedureName, parameters);
    }
}

package map.model.statements;

import map.MyException.MyException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.programState.*;
import map.model.types.Type;

public class NopStatement implements IStatement {

    /*
     * Constructor (empty)
     */
    public NopStatement() {
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "{nop}";
    }

    /*
     * Overriding execute(), the method inherited from the implemented interface
     * IStatement
     * Return null because this statement is supposed to do nothing (works the same
     * if we return state);
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
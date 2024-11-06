package model.statements;

import model.programState.*;
import MyException.MyException;

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
        return " {nop} ";
    }

    /*
     * Overriding execute(), the method inherited from the implemented interface
     * IStatement
     * Return null because this statement is supposed to do nothing (works the same
     * if we return state);
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
    }
}
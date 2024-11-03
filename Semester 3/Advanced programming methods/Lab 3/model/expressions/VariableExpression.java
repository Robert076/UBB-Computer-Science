package model.expressions;

import model.values.*;
import model.dataStructures.myDictionary.*;
import MyException.MyException;

public class VariableExpression implements Expression {
    /*
     * This expression is used as a wrapper
     * new PrintStatement(new VariableExpression("a"))
     */
    String id;

    /*
     * Constructor
     */
    public VariableExpression(String id) {
        this.id = id;
    }

    /*
     * Getter for id
     */
    public String getId() {
        return this.id;
    }

    /*
     * Setter for id
     */
    public void setId(String id) {
        this.id = id;
    }

    /*
     * Overriding eval. This is from the Expression interface
     * Simply returning the value of what is stored with our id in the symbol table
     */
    @Override
    public Value eval(MyIDictionary<String, Value> symTable) throws MyException {
        return symTable.lookup(id);
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "VariableExpression{id = " + this.id + "}";
    }
}

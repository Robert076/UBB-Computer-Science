package model.expressions;

import model.values.*;
import model.dataStructures.myDictionary.*;
import MyException.MyException;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        return table.lookup(id);
    }

    @Override
    public String toString() {
        return "VariableExpression{id = " + this.id + "}";
    }
}

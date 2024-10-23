package model.expressions;

import model.values.*;
import model.dataStructures.myDictionary.*;
import MyException.MyException;

public class VariableExpression implements Expression {
    String id;

    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        return table.lookup(id);
    }
}

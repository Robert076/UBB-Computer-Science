package model.expressions;

import model.values.*;
import model.dataStructures.myDictionary.*;
import MyException.MyException;

public class ValueExpression implements Expression {
    Value e;

    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        return e;
    }
}
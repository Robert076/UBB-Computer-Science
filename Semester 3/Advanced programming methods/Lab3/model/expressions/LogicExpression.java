package model.expressions;

import model.values.*;
import model.dataStructures.myDictionary.*;
import MyException.MyException;

public class LogicExpression implements Expression {
    Expression e1;
    Expression e2;
    int op; // 1 - &&, 2 - ||, 3 - !

    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        return new BoolValue(true);
    }
}
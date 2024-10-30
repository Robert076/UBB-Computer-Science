package model.expressions;

import model.values.*;
import model.dataStructures.myDictionary.*;
import MyException.MyException;

public class ValueExpression implements Expression {
    Value val;

    public ValueExpression(Value _val) {
        this.val = _val;
    }

    public Value getVal() {
        return this.val;
    }

    public void setVal(Value _val) {
        this.val = _val;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        return this.val;
    }

    @Override
    public String toString() {
        return "ValueExp{" + "val = " + this.val + "}";
    }
}
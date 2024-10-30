package model.expressions;

import model.dataStructures.myDictionary.*;
import MyException.MyException;
import model.values.*;

public interface Expression {
    Value eval(MyIDictionary<String, Value> table) throws MyException;

    String toString();
}
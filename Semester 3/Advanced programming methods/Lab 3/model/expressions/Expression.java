package model.expressions;

import model.dataStructures.myDictionary.*;
import model.dataStructures.myHeap.MyIHeap;
import MyException.InvalidOperation;
import MyException.MyException;
import model.values.*;

public interface Expression {
    Value eval(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException, InvalidOperation;

    String toString();

    Expression deepCopy();
}
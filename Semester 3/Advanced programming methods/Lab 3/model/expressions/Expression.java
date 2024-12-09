package model.expressions;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.*;
import model.dataStructures.myHeap.MyIHeap;
import model.types.Type;
import model.values.*;

public interface Expression {
    Value eval(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException, InvalidOperation;

    @Override
    String toString();

    Expression deepCopy();

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
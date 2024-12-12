package map.model.expressions;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import map.model.dataStructures.myDictionary.*;
import map.model.dataStructures.myHeap.MyIHeap;
import map.model.types.Type;
import map.model.values.*;

public interface Expression {
    Value eval(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException, InvalidOperation;

    @Override
    String toString();

    Expression deepCopy();

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
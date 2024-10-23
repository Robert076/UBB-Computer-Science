package model.expressions;

import model.dataStructures.myDictionary.*;
import model.values.*;
import model.types.*;
import MyException.MyException;

public class ArithmeticExpression implements Expression {
    Expression e1;
    Expression e2;
    int op; // 1 - plus, 2 - minus, 3 - star, 4 - divide

    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        Value v1, v2;
        v1 = e1.eval(table);

        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(table);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();

                if (op == 1)
                    return new IntValue(n1 + n2);
                else if (op == 2)
                    return new IntValue(n1 - n2);
                else if (op == 3)
                    return new IntValue(n1 * n2);
                else if (op == 4)
                    if (n2 == 0)
                        throw new MyException("Divison by zero");
                    else
                        return new IntValue(n1 / n2);
                else
                    throw new MyException("Invalid operand. Operand must be between 1-4 inclusive.");
            } else
                throw new MyException("Second operand is not an integer");
        } else
            throw new MyException("First operand is not an integer");
    }
}

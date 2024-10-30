package model.expressions;

import model.dataStructures.myDictionary.*;
import model.values.*;
import model.types.*;
import MyException.MyException;

enum ArithmeticOperator {
    PLUS, MINUS, MULTIPLY, DIVIDE
}

public class ArithmeticExpression implements Expression {
    Expression leftExp;
    Expression rightExp;
    ArithmeticOperator op;

    public ArithmeticExpression(Expression _leftExp, Expression _rightExp, ArithmeticOperator _op) {
        this.leftExp = _leftExp;
        this.rightExp = _rightExp;
        this.op = _op;
    }

    public Expression getLeftExp() {
        return this.leftExp;
    }

    public Expression getRightExp() {
        return this.rightExp;
    }

    public ArithmeticOperator getOperator() {
        return this.op;
    }

    public void setLeftExp(Expression _leftExp) {
        this.leftExp = _leftExp;
    }

    public void setRightExp(Expression _rightExp) {
        this.rightExp = _rightExp;
    }

    public void setOperator(ArithmeticOperator _op) {
        this.op = _op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        Value v1, v2;
        v1 = this.leftExp.eval(table);

        if (v1.getType().equals(new IntType())) {
            v2 = this.rightExp.eval(table);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();

                if (op == ArithmeticOperator.PLUS)
                    return new IntValue(n1 + n2);
                else if (op == ArithmeticOperator.MINUS)
                    return new IntValue(n1 - n2);
                else if (op == ArithmeticOperator.MULTIPLY)
                    return new IntValue(n1 * n2);
                else if (op == ArithmeticOperator.DIVIDE)
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

    @Override
    public String toString() {
        return "ArithmeticExpression{leftExp = " + this.leftExp + " , op = " + this.op + " , rightExp = "
                + this.rightExp + "}";
    }
}

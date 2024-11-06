package model.expressions;

import model.dataStructures.myDictionary.*;
import model.values.*;
import model.types.*;
import MyException.InvalidOperation;
import MyException.MyException;

public class ArithmeticExpression implements Expression {
    /*
     * leftExp op rightExp
     */
    Expression leftExp;
    Expression rightExp;
    ArithmeticOperator op;

    /*
     * Constructor
     */
    public ArithmeticExpression(Expression _leftExp, Expression _rightExp, ArithmeticOperator _op) {
        this.leftExp = _leftExp;
        this.rightExp = _rightExp;
        this.op = _op;
    }

    /*
     * Getter for left expression
     */
    public Expression getLeftExp() {
        return this.leftExp;
    }

    /*
     * Getter for right expression
     */
    public Expression getRightExp() {
        return this.rightExp;
    }

    /*
     * Getter for operator
     */
    public ArithmeticOperator getOperator() {
        return this.op;
    }

    /*
     * Setter for left expression
     */
    public void setLeftExp(Expression _leftExp) {
        this.leftExp = _leftExp;
    }

    /*
     * Setter for right expression
     */
    public void setRightExp(Expression _rightExp) {
        this.rightExp = _rightExp;
    }

    /*
     * Setter for operator
     */
    public void setOperator(ArithmeticOperator _op) {
        this.op = _op;
    }

    /*
     * Overriding eval method that we got from implementing Expression.
     * We check for types. Both leftExp.eval() and rightExp.eval() must have an
     * integer as the result
     * If at least one of them is not int, we raise exception
     * Otherwise we just check what operator we got and return
     * Whatever we got to return
     * Also very important we check for division by zero and that the operator is in
     * the enum.
     */
    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException, InvalidOperation {
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
                        throw new InvalidOperation("Divison by zero");
                    else
                        return new IntValue(n1 / n2);
                else
                    throw new MyException("Invalid operand. Operand must be between 1-4 inclusive.");
            } else
                throw new MyException("Second operand is not an integer");
        } else
            throw new MyException("First operand is not an integer");
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "ArithmeticExpression{leftExp = " + this.leftExp + " , op = " + this.op + " , rightExp = "
                + this.rightExp + "}";
    }
}

package map.model.expressions;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import map.model.dataStructures.myDictionary.*;
import map.model.dataStructures.myHeap.MyIHeap;
import map.model.types.*;
import map.model.values.*;

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
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap)
            throws MyException, InvalidOperation {
        Value v1, v2;

        v1 = this.leftExp.eval(table, heap);
        if (!v1.getType().equals(new IntType())) {
            throw new MyException("First operand is not an integer");
        }

        v2 = this.rightExp.eval(table, heap);
        if (!v2.getType().equals(new IntType())) {
            throw new MyException("Second operand is not an integer");
        }

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
        else if (op == ArithmeticOperator.DIVIDE) {
            if (n2 == 0) {
                throw new InvalidOperation("Divison by zero");
            }
            return new IntValue(n1 / n2);
        } else
            throw new MyException("Invalid arithmetic operator.");
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "ArithmeticExpression{leftExp = " + this.leftExp + " , op = " + this.op + " , rightExp = "
                + this.rightExp + "}";
    }

    @Override
    public ArithmeticExpression deepCopy() {
        return new ArithmeticExpression(this.leftExp, this.rightExp, this.op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = this.leftExp.typecheck(typeEnv);
        type2 = this.rightExp.typecheck(typeEnv);
        if (!type1.equals(new IntType())) {
            throw new MyException("First operand is not integer");
        }

        if (!type2.equals(new IntType())) {
            throw new MyException("Second operand is not integer");
        }

        return new IntType();
    }
}
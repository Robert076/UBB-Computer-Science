package model.expressions;

import model.values.*;
import model.dataStructures.myDictionary.*;
import model.dataStructures.myHeap.MyIHeap;
import model.types.BoolType;
import MyException.InvalidOperation;
import MyException.MyException;

public class LogicExpression implements Expression {
    /*
     * leftExp op rightExp
     */
    Expression leftExp;
    Expression rightExp;
    LogicalOperator op;

    /*
     * Constructor
     */
    public LogicExpression(Expression _leftExp, Expression _rightExp, LogicalOperator _op) {
        this.leftExp = _leftExp;
        this.rightExp = _rightExp;
        this.op = _op;
    }

    /*
     * Setter for leftExp
     */
    public void setLeftExp(Expression _leftExp) {
        this.leftExp = _leftExp;
    }

    /*
     * Setter for rightExp
     */
    public void setRightExp(Expression _rightExp) {
        this.rightExp = _rightExp;
    }

    /*
     * Setter for operator
     */
    public void setOperator(LogicalOperator _op) {
        this.op = _op;
    }

    /*
     * Getter for leftExp
     */
    public Expression getLeftExp() {
        return this.leftExp;
    }

    /*
     * Getter for rightExp
     */
    public Expression getRightExp() {
        return this.rightExp;
    }

    /*
     * Getter for operator
     */
    public LogicalOperator getOperator() {
        return this.op;
    }

    /*
     * Overriding the eval method we got from implementing the interface Expression
     * We evaluate left side. If the result is not a boolean, we throw exception
     * Same goes for right side
     * Once both are boolean, we check what operator we got
     * And return the according result.
     */
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap)
            throws MyException, InvalidOperation {
        Value leftVal = this.leftExp.eval(table, heap);
        Value rightVal = this.rightExp.eval(table, heap);

        if (!(leftVal.getType().equals(new BoolType())))
            throw new MyException("Invalid leftExp in logical expression");
        if (!(rightVal.getType().equals(new BoolType())))
            throw new MyException("Invalid rightExp in logical expression");

        BoolValue leftBool = (BoolValue) leftVal;
        BoolValue rightBool = (BoolValue) rightVal;

        if (op == LogicalOperator.AND) {
            if (leftBool.getVal() && rightBool.getVal())
                return new BoolValue(true);
            else
                return new BoolValue(false);
        } else {
            if (leftBool.getVal() || rightBool.getVal())
                return new BoolValue(true);
            else
                return new BoolValue(false);
        }
    }

    @Override
    public String toString() {
        return "LogicExpression{ leftExp = " + this.leftExp + " , op = " + this.op + " , rightExp = " + this.rightExp
                + "}";
    }

    @Override
    public LogicExpression deepCopy() {
        return new LogicExpression(this.leftExp, this.rightExp, this.op);
    }
}
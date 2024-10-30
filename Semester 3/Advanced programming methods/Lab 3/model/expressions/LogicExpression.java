package model.expressions;

import model.values.*;
import model.dataStructures.myDictionary.*;
import model.types.BoolType;
import MyException.MyException;

enum LogicalOperator {
    AND, OR
}

public class LogicExpression implements Expression {
    Expression leftExp;
    Expression rightExp;
    LogicalOperator op;

    public LogicExpression(Expression _leftExp, Expression _rightExp, LogicalOperator _op) {
        this.leftExp = _leftExp;
        this.rightExp = _rightExp;
        this.op = _op;
    }

    public void setLeftExp(Expression _leftExp) {
        this.leftExp = _leftExp;
    }

    public void setRightExp(Expression _rightExp) {
        this.rightExp = _rightExp;
    }

    public void setOperator(LogicalOperator _op) {
        this.op = _op;
    }

    public Expression getLeftExp() {
        return this.leftExp;
    }

    public Expression getRightExp() {
        return this.rightExp;
    }

    public LogicalOperator getOperator() {
        return this.op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        Value leftVal = this.leftExp.eval(table);
        Value rightVal = this.rightExp.eval(table);

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
}
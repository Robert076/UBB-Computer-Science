package model.expressions;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {
    Expression first, second;
    RelationalOperator op;

    public RelationalExpression(Expression _first, Expression _second, RelationalOperator _op) {
        this.first = _first;
        this.second = _second;
        this.op = _op;
    }

    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Integer, Value> heap)
            throws MyException, InvalidOperation {
        if (!this.first.eval(symTable, heap).getType().equals(new IntType())) {
            throw new MyException("First expression: " + this.first + " does not evaluate to an integer");
        }
        if (!this.second.eval(symTable, heap).getType().equals(new IntType())) {
            throw new MyException("Second expression: " + this.second + " does not evaluate to an integer");
        }

        IntValue val1 = (IntValue) this.first.eval(symTable, heap);
        IntValue val2 = (IntValue) this.second.eval(symTable, heap);

        if (this.op == RelationalOperator.L) {
            if (val1.getVal() < val2.getVal())
                return new BoolValue(true);
        } else if (this.op == RelationalOperator.LE) {
            if (val1.getVal() <= val2.getVal())
                return new BoolValue(true);
        } else if (this.op == RelationalOperator.E) {
            if (val1.getVal() == val2.getVal())
                return new BoolValue(true);
        } else if (this.op == RelationalOperator.GE) {
            if (val1.getVal() >= val2.getVal())
                return new BoolValue(true);
        } else if (this.op == RelationalOperator.G) {
            if (val1.getVal() > val2.getVal())
                return new BoolValue(true);
        }
        return new BoolValue(false);
    }

    public RelationalExpression deepCopy() {
        return new RelationalExpression(this.first.deepCopy(), this.second.deepCopy(), this.op);
    }

    public String toString() {
        return "RelationalExpression(" + this.first + " " + this.op + " " + this.second + ")";
    }
}
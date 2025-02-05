package model.expressions;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {
    Expression leftExp, rightExp;
    RelationalOperator op;

    public RelationalExpression(Expression _leftExp, Expression _rightExp, RelationalOperator _op) {
        this.leftExp = _leftExp;
        this.rightExp = _rightExp;
        this.op = _op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Integer, Value> heap)
            throws MyException, InvalidOperation {
        if (!this.leftExp.eval(symTable, heap).getType().equals(new IntType())) {
            throw new MyException("RelExp: leftExp expression: " + this.leftExp + " does not evaluate to an integer");
        }
        if (!this.rightExp.eval(symTable, heap).getType().equals(new IntType())) {
            throw new MyException("RelExp: rightExp expression: " + this.rightExp + " does not evaluate to an integer");
        }

        IntValue val1 = (IntValue) this.leftExp.eval(symTable, heap);
        IntValue val2 = (IntValue) this.rightExp.eval(symTable, heap);

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

    @Override
    public RelationalExpression deepCopy() {
        return new RelationalExpression(this.leftExp.deepCopy(), this.rightExp.deepCopy(), this.op);
    }

    @Override
    public String toString() {
        return "RelationalExpression(" + this.leftExp + " " + this.op + " " + this.rightExp + ")";
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
            throw new MyException("Second operand is not an integer");
        }

        return new BoolType();
    }
}
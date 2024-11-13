package model.expressions;

import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myDictionary.MyIDictionary;
import model.programState.ProgramState;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {
    IntValue first, second;
    RelationalOperator op;

    public RelationalExpression(IntValue _first, IntValue _second, RelationalOperator _op) {
        this.first = _first;
        this.second = _second;
        this.op = _op;
    }

    public Value eval(MyIDictionary<String, Value> symTable) {
        return new IntValue();
    }

    public RelationalExpression deepCopy() {
        return new RelationalExpression(this.first.deepCopy(), this.second.deepCopy(), this.op);
    }
}
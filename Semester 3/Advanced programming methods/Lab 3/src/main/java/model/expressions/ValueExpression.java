package model.expressions;

import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.types.Type;
import model.values.Value;

public class ValueExpression implements Expression {
    /*
     * This expression simply returns the value we give it
     * Its sort of a wrapper.
     * new AssignmentStatement("a", new ArithmeticExpression(new ValueExpression(new
     * IntValue(2)), new ValueExpression(new IntValue(4)),
     * ArithmeticOperator.MULTIPLY)),
     * This way we can assign both boolean and integer, and in the future whatever
     * else we have.
     */
    Value val;

    /*
     * Constructor
     */
    public ValueExpression(Value _val) {
        this.val = _val;
    }

    /*
     * Getter for val
     */
    public Value getVal() {
        return this.val;
    }

    /*
     * Setter for val
     */
    public void setVal(Value _val) {
        this.val = _val;
    }

    /*
     * Overriding eval which comes from Expression interface
     * Simply returning our value.
     * Remember this is a wrapper so we can assign various types of values
     */
    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Integer, Value> heap) throws MyException {
        return this.val;
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "ValueExp{" + "val = " + this.val + "}";
    }

    @Override
    public ValueExpression deepCopy() {
        return new ValueExpression(this.val);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return this.val.getType();
    }
}
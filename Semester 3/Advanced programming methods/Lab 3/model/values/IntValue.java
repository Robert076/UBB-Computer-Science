package model.values;

import model.types.*;

public class IntValue implements Value {
    int val;

    public IntValue() {
        this.val = 0;
    }

    public IntValue(int v) {
        this.val = v;
    }

    public int getVal() {
        return this.val;
    }

    @Override
    public String toString() {
        return Integer.toString(this.val);
    }

    @Override
    public Boolean equals(Value another) {
        if (!another.getType().equals(this.getType()))
            return false;
        return ((IntValue) another).getVal() == this.val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public IntValue deepCopy() {
        return new IntValue(this.val);
    }
}
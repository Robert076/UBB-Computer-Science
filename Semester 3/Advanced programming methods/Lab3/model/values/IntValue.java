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

    public String toString() {
        return Integer.toString(this.val);
    }

    public boolean equals(Value another) {
        if (!another.getType().equals(this.getType()))
            return false;
        return ((IntValue) another).getVal() == this.val;
    }

    public Type getType() {
        return new IntType();
    }
}
package model.values;

import model.types.*;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean v) {
        this.val = v;
    }

    public boolean getVal() {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

    @Override
    public boolean equals(Value another) {
        if (!another.getType().equals(this.getType())) {
            return false;
        }
        return ((BoolValue) another).getVal() == this.val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }
}
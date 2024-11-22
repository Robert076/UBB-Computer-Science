package model.values;

import model.types.*;

public class BoolValue implements Value {
    Boolean val;

    public BoolValue(boolean v) {
        this.val = v;
    }

    public Boolean getVal() {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

    @Override
    public Boolean equals(Value another) {
        if (!another.getType().equals(this.getType())) {
            return false;
        }
        return ((BoolValue) another).getVal() == this.val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(this.val);
    }
}
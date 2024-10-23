package model.values;

import model.types.*;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getVal() {
        return val;
    }

    public String toString() {
        return String.valueOf(val);
    }

    public Type getType() {
        return new BoolType();
    }
}
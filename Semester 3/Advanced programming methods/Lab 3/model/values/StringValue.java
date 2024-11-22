package model.values;

import model.types.*;

public class StringValue implements Value {
    String val;

    public StringValue(String v) {
        this.val = v;
    }

    public String getVal() {
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
        return ((StringValue) another).getVal() == this.val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public Value deepCopy() {
        return new StringValue(this.val);
    }
}
package model.types;

import model.values.BoolValue;

public class BoolType implements Type {
    public BoolType() {
    }

    @Override
    public boolean equals(Type another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "BoolType";
    }

    @Override
    public BoolValue defaultValue() {
        return new BoolValue(false);
    }
}
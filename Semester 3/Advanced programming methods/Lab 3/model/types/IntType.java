package model.types;

import model.values.IntValue;

public class IntType implements Type {
    public IntType() {
    }

    @Override
    public boolean equals(Type another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "IntType";
    }

    @Override
    public IntValue defaultValue() {
        return new IntValue(0);
    }
}
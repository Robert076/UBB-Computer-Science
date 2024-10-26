package model.types;

public class BoolType implements Type {
    public BoolType() {
    }

    public boolean equals(Type another) {
        return another instanceof BoolType;
    }

    public String toString() {
        return "BoolType";
    }
}
package model.types;

public class IntType implements Type {
    public IntType() {
    }

    public boolean equals(Type another) {
        return another instanceof IntType;
    }

    public String toString() {
        return "IntType";
    }
}
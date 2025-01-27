package model.types;

import model.values.StringValue;

public class StringType implements Type {
    public StringType() {

    }

    @Override
    public Boolean equals(Type another) {
        return another instanceof StringType;
    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public StringValue defaultValue() {
        return new StringValue("");
    }
}

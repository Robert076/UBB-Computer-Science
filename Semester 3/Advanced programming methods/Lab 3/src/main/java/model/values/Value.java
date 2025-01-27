package model.values;

import model.types.Type;

public interface Value {
    String toString();

    Boolean equals(Value another);

    Type getType();

    Value deepCopy();
}

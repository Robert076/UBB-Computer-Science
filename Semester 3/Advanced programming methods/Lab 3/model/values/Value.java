package model.values;

import model.types.*;

public interface Value {
    String toString();

    boolean equals(Value another);

    Type getType();
}

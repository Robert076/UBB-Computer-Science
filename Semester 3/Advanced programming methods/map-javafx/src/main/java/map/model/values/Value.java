package map.model.values;

import map.model.types.*;

public interface Value {
    String toString();

    Boolean equals(Value another);

    Type getType();

    Value deepCopy();
}

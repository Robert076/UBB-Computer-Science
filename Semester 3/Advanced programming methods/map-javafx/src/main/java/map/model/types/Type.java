package map.model.types;

import map.model.values.Value;

public interface Type {
    String toString();

    public Boolean equals(Type another);

    public Value defaultValue();
}
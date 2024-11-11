package model.types;

import model.values.Value;

public interface Type {
    String toString();

    public Boolean equals(Type another);

    public Value defaultValue();
}
package model.types;

import model.values.Value;

public interface Type {
    String toString();

    public boolean equals(Type another);

    public Value defaultValue();
}
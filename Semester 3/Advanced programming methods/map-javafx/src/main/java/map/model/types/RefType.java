package map.model.types;

import map.model.values.RefValue;
import map.model.values.Value;

public class RefType implements Type {
    Type inner;

    public RefType(Type _inner) {
        this.inner = _inner;
    }

    public Type getInner() {
        return this.inner;
    }

    public Boolean equals(Type another) {
        if (another instanceof RefType) {
            RefType _another = (RefType) another;
            return this.inner.equals(_another.getInner());
        } else
            return false;
    }

    public String toString() {
        return "Ref(" + this.inner.toString() + ")";
    }

    public Value defaultValue() {
        return new RefValue(0, inner);
    }
}

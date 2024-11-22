package model.values;

import model.types.Type;
import model.types.RefType;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int _address, Type _locationType) {
        this.address = _address;
        this.locationType = _locationType;
    }

    public int getAddr() {
        return this.address;
    }

    public Type getType() {
        return new RefType(this.locationType);
    }

    public Type getLocationType() {
        return this.locationType;
    }

    public Boolean equals(Value another) {
        if (another instanceof RefValue) {
            return this.address == ((RefValue) another).getAddr()
                    && this.locationType.equals(((RefValue) another).getType());
        }
        return false;
    }

    @Override
    public String toString() {
        return "RefValue(" + this.address + ", " + this.locationType + ")";
    }

    @Override
    public RefValue deepCopy() {
        return new RefValue(this.address, this.locationType);
    }
}

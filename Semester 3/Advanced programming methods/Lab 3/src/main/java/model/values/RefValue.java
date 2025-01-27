package model.values;

import model.types.RefType;
import model.types.Type;

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

    @Override
    public Type getType() {
        return new RefType(this.locationType);
    }

    public Type getLocationType() {
        return this.locationType;
    }

    @Override
    public Boolean equals(Value another) {
        if (another instanceof RefValue refValue) {
            return this.address == refValue.getAddr()
                    && this.locationType.equals(refValue.getType());
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

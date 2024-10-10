package Repository;

import Model.IEnt;
import MyException.MyException;

public class Repo implements IRepo {
    private IEnt[] elements;
    Integer currPos;

    public Repo(int capacity) {
        this.elements = new IEnt[capacity];
        this.currPos = 0;
    }

    public void writeEntity(IEnt entity) throws MyException {
        if (this.currPos == this.elements.length)
            throw new MyException("Repository is full!");
        else
            elements[currPos++] = entity;
    }

    public IEnt readEntity(Integer pos) throws MyException {
        if (pos >= elements.length)
            throw new MyException("Invalid position for read");
        else
            return elements[pos];
    }

    public void deleteEntity(Integer pos) throws MyException {
        if (pos >= elements.length) {
            throw new MyException("Invalid position for delete");
        }
        IEnt[] newElements = new IEnt[elements.length];
        for (int i = 0, k = 0; i < elements.length; i++) {
            if (i != pos) {
                newElements[k] = elements[i];
                k++;
            }
        }
        elements = newElements;
    }

    public IEnt[] getAll() {
        IEnt[] copyOfElements = new IEnt[elements.length];

        for (int i = 0; i < elements.length; i++) {
            copyOfElements[i] = elements[i];
        }

        return copyOfElements;
    }

    public Integer getCurrentPos() {
        return this.currPos;
    }
}

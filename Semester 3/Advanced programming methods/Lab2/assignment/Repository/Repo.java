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
            throw new MyException("Invalid position");
        else
            return elements[pos];
    }

    public IEnt[] getAll() {
        IEnt[] copyOfElements = new IEnt[elements.length];

        for (int i = 0; i < elements.length; i++) {
            copyOfElements[i] = elements[i];
        }

        return copyOfElements;
    }
}
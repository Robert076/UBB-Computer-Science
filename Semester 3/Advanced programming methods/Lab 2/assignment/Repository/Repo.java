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
        if (pos >= currPos)
            throw new MyException("Invalid position for read");
        else
            return elements[pos];
    }

    public void deleteEntity(Integer pos) throws MyException {
        if (pos < 0 || pos >= currPos) {
            throw new MyException("Invalid position for delete");
        }

        for (int i = pos; i < currPos - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[--currPos] = null;
    }

    public IEnt[] getAll() {
        IEnt[] copyOfElements = new IEnt[currPos];

        for (int i = 0; i < currPos; i++) {
            copyOfElements[i] = elements[i];
        }

        return copyOfElements;
    }

    public Integer getCurrentPos() {
        return this.currPos;
    }
}

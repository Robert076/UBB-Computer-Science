package Repository;

import Model.IEnt;
import MyException.MyException;

public interface IRepo {
    public void writeEntity(IEnt entity) throws MyException;

    public IEnt readEntity(Integer pos) throws MyException;

    public IEnt[] getAll();
}

package Controller;

import Model.IEnt;
import MyException.MyException;
import Repository.IRepo;

public class Controller {
    IRepo repo;

    public Controller(IRepo repository) {
        this.repo = repository;
    }

    public void writeEntity(IEnt entity) {
        try {
            this.repo.writeEntity(entity);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    public IEnt readEntity(Integer pos) {
        try {
            return this.repo.readEntity(pos);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

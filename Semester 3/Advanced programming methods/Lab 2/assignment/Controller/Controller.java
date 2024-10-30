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

    public IEnt[] getPricesAbove20() {
        IEnt entities[] = this.repo.getAll();
        IEnt result[] = new IEnt[entities.length];
        int k = 0;
        Integer size = this.repo.getCurrentPos();
        for (int i = 0; i < size; i++) {
            if (entities[i].Compute() > 200) {
                result[k++] = entities[i];
            }
        }
        IEnt[] finalResult = new IEnt[k];
        for (int i = 0; i < k; i++) {
            finalResult[i] = result[i];
        }

        return finalResult;
    }

    public Boolean deleteEntity(Integer pos) {
        try {
            this.repo.deleteEntity(pos);
            return true;
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

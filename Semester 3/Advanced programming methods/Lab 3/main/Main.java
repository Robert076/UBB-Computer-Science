package main;

import repository.IRepository;
import repository.Repository;
import controller.Controller;
import view.View;

public class Main {
    public static void main(String[] args) {
        IRepository repo = new Repository();
        Controller controller = new Controller(repo);
        View view = new View(controller);
        // run
        view.runApp();
    }
}
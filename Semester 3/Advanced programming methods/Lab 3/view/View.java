package view;

import controller.Controller;
import model.programState.ProgramState;
import model.statements.IStatement;

public class View {
    // print meniu
    // run program
    // start program
    Controller controller;

    public View(Controller _controller) {
        this.controller = _controller;
    }

    public void printMenu() {
        String[] outputs = { "Welcome to the toy interpreter language! Please pick an option", "1. Run program 1",
                "2. Run program 2\n" };
        for (String out : outputs)
            System.out.println(out);
    }

    public void runApp() {
        this.printMenu();

        ProgramState program1 = new ProgramState();
    }
}

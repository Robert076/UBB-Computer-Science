package view.command;

import java.io.IOException;

import MyException.InvalidOperation;
import MyException.MyException;
import controller.Controller;
import model.statements.IStatement;

public class RunExample extends Command {
    Controller controller;

    public RunExample(String _key, IStatement _statement, Controller _controller) {
        super(_key, _statement.toString());
        this.controller = _controller;
    }

    @Override
    public void execute() {
        try {
            controller.fullExecution();
        } catch (MyException | InvalidOperation | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

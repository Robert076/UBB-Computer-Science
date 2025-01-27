package view.command;

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
        this.controller.allStep();
    }
}

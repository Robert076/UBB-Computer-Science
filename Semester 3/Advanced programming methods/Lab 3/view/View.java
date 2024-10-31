package view;

import java.util.Scanner;

import MyException.MyException;
import controller.Controller;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myList.MyList;
import model.dataStructures.myStack.MyStack;
import model.expressions.*;
import model.programState.ProgramState;
import model.statements.*;
import model.types.*;
import model.values.*;

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
        IStatement ex1 = new CompoundStatement(new VarDeclStatement("c", new IntType()),
                new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("c"))));
        MyStack<IStatement> exeStack = new MyStack<IStatement>();
        exeStack.push(ex1);
        MyDictionary<String, Value> symTable = new MyDictionary<String, Value>();
        MyList<Value> out = new MyList<Value>();
        System.out.println(exeStack);
        ProgramState prgState = new ProgramState(exeStack, symTable, out, ex1);
        try {
            this.controller.setCurrentProgram(prgState);
            controller.fullExecution();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(symTable);
        System.out.println(out);
    }
}

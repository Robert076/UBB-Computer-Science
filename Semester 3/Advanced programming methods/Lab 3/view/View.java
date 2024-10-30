package view;

import java.util.Scanner;

import controller.Controller;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myList.MyList;
import model.dataStructures.myStack.MyStack;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.programState.ProgramState;
import model.statements.AssignmentStatement;
import model.statements.CompoundStatement;
import model.statements.IStatement;
import model.statements.PrintStatement;
import model.statements.VarDeclStatement;
import model.types.IntType;
import model.values.IntValue;
import model.values.Value;

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your choice: ");
        String choice = scanner.nextLine();
        System.out.println(choice);
        IStatement ex1 = new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
    }
}

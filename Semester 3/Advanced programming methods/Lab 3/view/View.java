package view;

import java.util.Scanner;

import MyException.InvalidOperation;
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
        // program 1
        this.printMenu();
        IStatement ex1 = new CompoundStatement(new VarDeclStatement("c", new IntType()),
                new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("c"))));
        // program 2
        IStatement ex2 = new CompoundStatement(new VarDeclStatement("a", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("a",
                                new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                        new ValueExpression(new IntValue(4)), ArithmeticOperator.MULTIPLY)),
                        new PrintStatement(new VariableExpression("a"))));

        MyStack<IStatement> exeStack = new MyStack<IStatement>();
        MyDictionary<String, Value> symTable = new MyDictionary<String, Value>();
        MyList<Value> out = new MyList<Value>();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Your choice: ");
        Integer choice = Integer.parseInt(scanner.nextLine());
        scanner.close();

        IStatement programToRun;

        if (choice == 1)
            programToRun = ex1;
        else if (choice == 2)
            programToRun = ex2;
        else {
            System.out.println("Invalid input");
            return;
        }

        this.controller.setDisplayFlag(true);

        ProgramState prgState = new ProgramState(exeStack, symTable, out, programToRun);

        try {
            this.controller.setCurrentProgram(prgState);
            this.controller.fullExecution();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        } catch (InvalidOperation e) {
            System.out.println(e.getMessage());
        }
    }
}

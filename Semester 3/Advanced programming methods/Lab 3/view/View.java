package view;

import java.io.BufferedReader;
import controller.Controller;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myList.MyIList;
import model.dataStructures.myList.MyList;
import model.dataStructures.myStack.MyIStack;
import model.dataStructures.myStack.MyStack;
import model.expressions.*;
import model.programState.ProgramState;
import model.statements.*;
import model.types.*;
import model.values.*;
import repository.IRepository;
import repository.Repository;
import view.command.ExitCommand;
import view.command.RunExample;

public class View {
    private static IStatement createExample1() {
        // int v; v = 2; Print(v)
        return new CompoundStatement(
                new VarDeclStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
    }

    private static IStatement createExample2() {
        // int a; int b; a = 5; b = 2; int c; c = a / b; Print(c);
        return new CompoundStatement(
                new VarDeclStatement("a", new IntType()),
                new CompoundStatement(
                        new VarDeclStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new IntValue(5))),
                                new CompoundStatement(
                                        new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
                                        new CompoundStatement(new VarDeclStatement("c", new IntType()),
                                                new CompoundStatement(
                                                        new AssignmentStatement("c",
                                                                new ArithmeticExpression(new VariableExpression("a"),
                                                                        new VariableExpression("b"),
                                                                        ArithmeticOperator.DIVIDE)),
                                                        new PrintStatement(new VariableExpression("c"))))))));
    }

    private static IStatement createExample3() {
        return new CompoundStatement(new VarDeclStatement("file", new StringType()),
                new CompoundStatement(new AssignmentStatement("file", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFile(new VariableExpression("file")),
                                new CompoundStatement(new VarDeclStatement("a", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("file"), "a"),
                                                new PrintStatement(new VariableExpression("a")))))));
    }

    private static ProgramState createProgramState(IStatement originalProgram) {
        MyIStack<IStatement> exeStack = new MyStack<>();
        MyIDictionary<String, Value> symTable = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();

        return new ProgramState(exeStack, symTable, out, originalProgram, fileTable);
    }

    private static Controller createController(IStatement _statement, String _logFilePath) {
        ProgramState prgState = createProgramState(_statement);
        IRepository repo = new Repository(prgState, _logFilePath);
        return new Controller(repo);
    }

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

        menu.addCommand(new RunExample("1", createExample1(), createController(createExample1(), "log1.txt")));
        menu.addCommand(new RunExample("2", createExample2(), createController(createExample2(), "log2.txt")));
        menu.addCommand(new RunExample("3", createExample3(), createController(createExample3(), "log3.txt")));
        menu.addCommand(new ExitCommand("0", "Exit"));

        menu.show();
    }
}

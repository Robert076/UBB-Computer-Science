package view;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.Controller;
import javafx.util.Pair;
import model.dataStructures.myDictionary.MyDictionary;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myFileTable.MyFileTable;
import model.dataStructures.myFileTable.MyIFileTable;
import model.dataStructures.myHeap.MyHeap;
import model.dataStructures.myHeap.MyIHeap;
import model.dataStructures.myList.MyIList;
import model.dataStructures.myList.MyList;
import model.dataStructures.myProcedureTable.MyIProcedureTable;
import model.dataStructures.myProcedureTable.MyProcedureTable;
import model.dataStructures.myStack.MyIStack;
import model.dataStructures.myStack.MyStack;
import model.expressions.ArithmeticExpression;
import model.expressions.ArithmeticOperator;
import model.expressions.ReadHeapExpression;
import model.expressions.RelationalExpression;
import model.expressions.RelationalOperator;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.programState.ProgramState;
import model.statements.AssignmentStatement;
import model.statements.CallProcedureStatement;
import model.statements.CloseRFile;
import model.statements.CompoundStatement;
import model.statements.ForkStatement;
import model.statements.HeapAllocStatement;
import model.statements.IStatement;
import model.statements.OpenRFile;
import model.statements.PrintStatement;
import model.statements.ReadFile;
import model.statements.VarDeclStatement;
import model.statements.WhileStatement;
import model.statements.WriteHeapStatement;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;
import repository.IRepository;
import repository.Repository;
import view.command.Command;
import view.command.ExitCommand;
import view.command.RunExample;

public class View {
        public static void addProcedures(MyIProcedureTable procTable) {

                IStatement sumProcBody = new CompoundStatement(
                                new VarDeclStatement("v", new IntType()),
                                new CompoundStatement(
                                                new AssignmentStatement("v",
                                                                new ArithmeticExpression(new VariableExpression("a"),
                                                                                new VariableExpression("b"),
                                                                                ArithmeticOperator.PLUS)),
                                                new PrintStatement(new VariableExpression("v"))));

                List<String> varSum = new ArrayList<>();
                varSum.add("a");
                varSum.add("b");
                procTable.update("sum", new Pair<>(varSum, sumProcBody));

                IStatement prodProcBody = new CompoundStatement(
                                new VarDeclStatement("v", new IntType()),
                                new CompoundStatement(
                                                new AssignmentStatement("v",
                                                                new ArithmeticExpression(new VariableExpression("a"),
                                                                                new VariableExpression("b"),
                                                                                ArithmeticOperator.MULTIPLY)),
                                                new PrintStatement(new VariableExpression("v"))));

                List<String> varProd = new ArrayList<>();
                varProd.add("a");
                varProd.add("b");
                procTable.update("product", new Pair<>(varProd, prodProcBody));
        }

        public static IStatement createExample8() {
                return new CompoundStatement(new VarDeclStatement("v", new IntType()),
                                new CompoundStatement(new VarDeclStatement("w", new IntType()), new CompoundStatement(
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(
                                                                new AssignmentStatement("w",
                                                                                new ValueExpression(new IntValue(5))),
                                                                new CallProcedureStatement("sum",
                                                                                new ArrayList<>(Arrays.asList(
                                                                                                new VariableExpression(
                                                                                                                "v"),
                                                                                                new VariableExpression(
                                                                                                                "w"))))))));
                // return new CompoundStatement(
                // new VarDeclStatement("v", new IntType()),
                // new CompoundStatement(
                // new VarDeclStatement("w", new IntType()),
                // new CompoundStatement(
                // new AssignmentStatement("v",
                // new ValueExpression(new IntValue(2))),
                // new CompoundStatement(
                // new AssignmentStatement("w",
                // new ValueExpression(
                // new IntValue(5))),
                // new CompoundStatement(
                // new CallProcedureStatement(
                // "sum",
                // new ArrayList<>(Arrays
                // .asList(new ArithmeticExpression(
                // new VariableExpression(
                // "v"),
                // new ValueExpression(
                // new IntValue(10)),
                // ArithmeticOperator.MULTIPLY),
                // new VariableExpression(
                // "w")))),
                // new CompoundStatement(
                // new PrintStatement(
                // new VariableExpression(
                // "v")),
                // new ForkStatement(
                // new CompoundStatement(
                // new CallProcedureStatement(
                // "product",
                // new ArrayList<>(Arrays
                // .asList(new VariableExpression(
                // "v"),
                // new VariableExpression(
                // "w")))),
                // new ForkStatement(
                // new CallProcedureStatement(
                // "sum",
                // new ArrayList<>(Arrays
                // .asList(new VariableExpression(
                // "v"),
                // new VariableExpression(
                // "w")))))))))))));
        }

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
                                                                new AssignmentStatement("a",
                                                                                new ValueExpression(new IntValue(5))),
                                                                new CompoundStatement(
                                                                                new AssignmentStatement("b",
                                                                                                new ValueExpression(
                                                                                                                new IntValue(5))),
                                                                                new CompoundStatement(
                                                                                                new VarDeclStatement(
                                                                                                                "c",
                                                                                                                new IntType()),
                                                                                                new CompoundStatement(
                                                                                                                new AssignmentStatement(
                                                                                                                                "c",
                                                                                                                                new ArithmeticExpression(
                                                                                                                                                new VariableExpression(
                                                                                                                                                                "a"),
                                                                                                                                                new VariableExpression(
                                                                                                                                                                "b"),
                                                                                                                                                ArithmeticOperator.DIVIDE)),
                                                                                                                new CompoundStatement(
                                                                                                                                new PrintStatement(
                                                                                                                                                new VariableExpression(
                                                                                                                                                                "c")),
                                                                                                                                new PrintStatement(
                                                                                                                                                new VariableExpression(
                                                                                                                                                                "b")))))))));
        }

        private static IStatement createExample3() {
                return new CompoundStatement(new VarDeclStatement("file", new StringType()),
                                new CompoundStatement(
                                                new AssignmentStatement("file",
                                                                new ValueExpression(new StringValue("test.in"))),
                                                new CompoundStatement(new OpenRFile(new VariableExpression("file")),
                                                                new CompoundStatement(
                                                                                new VarDeclStatement("a",
                                                                                                new IntType()),
                                                                                new CompoundStatement(new ReadFile(
                                                                                                new VariableExpression(
                                                                                                                "file"),
                                                                                                "a"),
                                                                                                new CompoundStatement(
                                                                                                                new PrintStatement(
                                                                                                                                new VariableExpression(
                                                                                                                                                "a")),
                                                                                                                new CompoundStatement(
                                                                                                                                new CloseRFile(new VariableExpression(
                                                                                                                                                "file")),
                                                                                                                                new PrintStatement(
                                                                                                                                                new VariableExpression(
                                                                                                                                                                "file")))))))));
        }

        private static IStatement createExample4() {
                // Ref int v; writeHeap(v,20); Ref Ref int a; writeHeap(a,v); print(v); print(a)
                return new CompoundStatement(
                                new VarDeclStatement("v", new RefType(new IntType())),
                                new CompoundStatement(
                                                new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                                                new CompoundStatement(
                                                                new VarDeclStatement("a",
                                                                                new RefType(new RefType(
                                                                                                new IntType()))),
                                                                new CompoundStatement(
                                                                                new HeapAllocStatement("a",
                                                                                                new VariableExpression(
                                                                                                                "v")),
                                                                                new CompoundStatement(
                                                                                                new PrintStatement(
                                                                                                                new VariableExpression(
                                                                                                                                "v")),
                                                                                                new PrintStatement(
                                                                                                                new VariableExpression(
                                                                                                                                "a")))))));
        }

        private static IStatement createExample5() {
                return new CompoundStatement(new VarDeclStatement("v", new IntType()), new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntValue(5))),
                                new CompoundStatement(
                                                new WhileStatement(new RelationalExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntValue(0)),
                                                                RelationalOperator.GE),
                                                                new CompoundStatement(new PrintStatement(
                                                                                new VariableExpression("v")),
                                                                                new AssignmentStatement("v",
                                                                                                new ArithmeticExpression(
                                                                                                                new VariableExpression(
                                                                                                                                "v"),
                                                                                                                new ValueExpression(
                                                                                                                                new IntValue(1)),
                                                                                                                ArithmeticOperator.MINUS)))),
                                                new PrintStatement(new VariableExpression("v")))));
        }

        private static IStatement createExample6() {
                // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
                return new CompoundStatement(
                                new VarDeclStatement("v", new RefType(new IntType())),
                                new CompoundStatement(
                                                new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                                                new CompoundStatement(
                                                                new VarDeclStatement("a",
                                                                                new RefType(new RefType(
                                                                                                new IntType()))),
                                                                new CompoundStatement(
                                                                                new HeapAllocStatement("a",
                                                                                                new VariableExpression(
                                                                                                                "v")),
                                                                                new CompoundStatement(
                                                                                                new HeapAllocStatement(
                                                                                                                "v",
                                                                                                                new ValueExpression(
                                                                                                                                new IntValue(30))),
                                                                                                new PrintStatement(
                                                                                                                new ReadHeapExpression(
                                                                                                                                new ReadHeapExpression(
                                                                                                                                                new VariableExpression(
                                                                                                                                                                "a")))))))));
        }

        private static IStatement createExample7() {
                return new CompoundStatement(new VarDeclStatement("v", new IntType()), new CompoundStatement(
                                new VarDeclStatement("a", new RefType(new IntType())),
                                new CompoundStatement(
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                                new CompoundStatement(
                                                                new HeapAllocStatement("a",
                                                                                new ValueExpression(new IntValue(22))),
                                                                new CompoundStatement(
                                                                                new ForkStatement(new CompoundStatement(
                                                                                                new WriteHeapStatement(
                                                                                                                "a",
                                                                                                                new ValueExpression(
                                                                                                                                new IntValue(30))),
                                                                                                new CompoundStatement(
                                                                                                                new AssignmentStatement(
                                                                                                                                "v",
                                                                                                                                new ValueExpression(
                                                                                                                                                new IntValue(32))),
                                                                                                                new CompoundStatement(
                                                                                                                                new PrintStatement(
                                                                                                                                                new VariableExpression(
                                                                                                                                                                "v")),
                                                                                                                                new PrintStatement(
                                                                                                                                                new ReadHeapExpression(
                                                                                                                                                                new VariableExpression(
                                                                                                                                                                                "a"))))))),
                                                                                new CompoundStatement(
                                                                                                new PrintStatement(
                                                                                                                new VariableExpression(
                                                                                                                                "v")),
                                                                                                new PrintStatement(
                                                                                                                new ReadHeapExpression(
                                                                                                                                new VariableExpression(
                                                                                                                                                "a")))))))));
        }

        private static ProgramState createProgramState(IStatement originalProgram) {
                MyIStack<IStatement> exeStack = new MyStack<>();
                MyIStack<MyIDictionary<String, Value>> symTableStack = new MyStack<>();

                // Push an empty dictionary as the initial scope
                symTableStack.push(new MyDictionary<>());

                MyIList<Value> out = new MyList<>();
                MyIFileTable<StringValue, BufferedReader> fileTable = new MyFileTable<>();
                MyIHeap<Integer, Value> heap = new MyHeap<>();
                MyIProcedureTable procedureTable = new MyProcedureTable();
                addProcedures(procedureTable);

                return new ProgramState(exeStack, symTableStack, out, originalProgram, fileTable, heap, procedureTable);
        }

        private static Controller createController(IStatement _statement, String _logFilePath) {
                ProgramState prgState = createProgramState(_statement);
                List<ProgramState> originalList = new ArrayList<>();
                originalList.add(prgState);
                IRepository repo = new Repository(_logFilePath);
                repo.setPrgList(originalList);
                return new Controller(repo);
        }

        public static TextMenu createTextMenu() {
                Controller ctr1 = createController(createExample1(), "log1.txt");
                Controller ctr2 = createController(createExample2(), "log2.txt");
                Controller ctr3 = createController(createExample3(), "log3.txt");
                Controller ctr4 = createController(createExample4(), "log4.txt");
                Controller ctr5 = createController(createExample5(), "log5.txt");
                Controller ctr6 = createController(createExample6(), "log6.txt");
                Controller ctr7 = createController(createExample7(), "log7.txt");
                Controller ctr8 = createController(createExample8(), "log8.txt");

                Command cmm1 = new RunExample("1", createExample1(), ctr1);
                Command cmm2 = new RunExample("2", createExample2(), ctr2);
                Command cmm3 = new RunExample("3", createExample3(), ctr3);
                Command cmm4 = new RunExample("4", createExample4(), ctr4);
                Command cmm5 = new RunExample("5", createExample5(), ctr5);
                Command cmm6 = new RunExample("6", createExample6(), ctr6);
                Command cmm7 = new RunExample("7", createExample7(), ctr7);
                Command cmm8 = new RunExample("8", createExample8(), ctr8);

                TextMenu textMenu = new TextMenu();
                textMenu.addCommand(cmm1);
                textMenu.addCommand(cmm2);
                textMenu.addCommand(cmm3);
                textMenu.addCommand(cmm4);
                textMenu.addCommand(cmm5);
                textMenu.addCommand(cmm6);
                textMenu.addCommand(cmm7);
                textMenu.addCommand(cmm8);
                textMenu.addCommand(new ExitCommand("0", "Exit"));

                return textMenu;
        }

        public static Controller createControllerForGUI(String key) {
                return switch (key) {
                        case "1" -> createController(createExample1(), "log1.txt");
                        case "2" -> createController(createExample2(), "log2.txt");
                        case "3" -> createController(createExample3(), "log3.txt");
                        case "4" -> createController(createExample4(), "log4.txt");
                        case "5" -> createController(createExample5(), "log5.txt");
                        case "6" -> createController(createExample6(), "log6.txt");
                        case "7" -> createController(createExample7(), "log7.txt");
                        case "8" -> createController(createExample8(), "log8.txt");
                        default -> null;
                };

        }
}

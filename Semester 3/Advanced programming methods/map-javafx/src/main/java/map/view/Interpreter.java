package map.view;

import map.MyException.MyException;
import map.controller.Controller;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import map.model.dataStructures.myDictionary.MyDictionary;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.dataStructures.myFileTable.MyFileTable;
import map.model.dataStructures.myFileTable.MyIFileTable;
import map.model.dataStructures.myHeap.MyHeap;
import map.model.dataStructures.myHeap.MyIHeap;
import map.model.dataStructures.myList.MyIList;
import map.model.dataStructures.myList.MyList;
import map.model.dataStructures.myStack.MyIStack;
import map.model.dataStructures.myStack.MyStack;
import map.model.expressions.*;
import map.model.programState.ProgramState;
import map.model.statements.*;
import map.model.types.*;
import map.model.values.*;
import map.repository.IRepository;
import map.repository.Repository;
import map.view.command.ExitCommand;
import map.view.command.RunExample;

public class Interpreter {
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
                MyIDictionary<String, Value> symTable = new MyDictionary<>();
                MyIList<Value> out = new MyList<>();
                MyIFileTable<StringValue, BufferedReader> fileTable = new MyFileTable<>();
                MyIHeap<Integer, Value> heap = new MyHeap<>();
                return new ProgramState(exeStack, symTable, out, originalProgram, fileTable, heap);
        }

        private static Controller createController(IStatement _statement, String _logFilePath) {
                ProgramState prgState = createProgramState(_statement);
                List<ProgramState> originalList = new ArrayList<>();
                originalList.add(prgState);
                IRepository repo = new Repository(_logFilePath);
                repo.setPrgList(originalList);
                return new Controller(repo);
        }

        public static void main(String[] args) {
                TextMenu menu = new TextMenu();

                IStatement IStmt1 = createExample1();
                try {
                        IStmt1.typecheck(new MyDictionary<String, Type>());
                        menu.addCommand(new RunExample("1", IStmt1, createController(IStmt1, "log1.txt")));
                } catch (MyException e) {
                        System.out.println("TYPECHECK_ERR: Error in IStmt1 - " + e.getMessage());
                        return;
                }

                IStatement IStmt2 = createExample2();
                try {
                        IStmt2.typecheck(new MyDictionary<String, Type>());
                        menu.addCommand(new RunExample("2", IStmt2, createController(IStmt2, "log2.txt")));
                } catch (MyException e) {
                        System.out.println("TYPECHECK_ERR: Error in IStmt2 - " + e.getMessage());
                        return;
                }

                IStatement IStmt3 = createExample3();
                try {
                        IStmt3.typecheck(new MyDictionary<String, Type>());
                        menu.addCommand(new RunExample("3", IStmt3, createController(IStmt3, "log3.txt")));
                } catch (MyException e) {
                        System.out.println("TYPECHECK_ERR: Error in IStmt3 - " + e.getMessage());
                        return;
                }

                IStatement IStmt4 = createExample4();
                try {
                        IStmt4.typecheck(new MyDictionary<String, Type>());
                        menu.addCommand(new RunExample("4", IStmt4, createController(IStmt4, "log4.txt")));
                } catch (MyException e) {
                        System.out.println("TYPECHECK_ERR: Error in IStmt4 - " + e.getMessage());
                        return;
                }

                IStatement IStmt5 = createExample5();
                try {
                        IStmt5.typecheck(new MyDictionary<String, Type>());
                        menu.addCommand(new RunExample("5", IStmt5, createController(IStmt5, "log5.txt")));
                } catch (MyException e) {
                        System.out.println("TYPECHECK_ERR: Error in IStmt5 - " + e.getMessage());
                        return;
                }

                IStatement IStmt6 = createExample6();
                try {
                        IStmt6.typecheck(new MyDictionary<String, Type>());
                        menu.addCommand(new RunExample("6", IStmt6, createController(IStmt6, "log6.txt")));
                } catch (MyException e) {
                        System.out.println("TYPECHECK_ERR: Error in IStmt6 - " + e.getMessage());
                        return;
                }

                IStatement IStmt7 = createExample7();
                try {
                        IStmt7.typecheck(new MyDictionary<String, Type>());
                        menu.addCommand(new RunExample("7", IStmt7, createController(IStmt7, "log7.txt")));
                } catch (MyException e) {
                        System.out.println("TYPECHECK_ERR: Error in IStmt7 - " + e.getMessage());
                        return;
                }

                menu.addCommand(new ExitCommand("0", "Exit"));

                menu.show();
        }
}

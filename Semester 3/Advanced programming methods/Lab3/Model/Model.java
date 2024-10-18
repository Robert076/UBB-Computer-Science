package Model;

import MyException.MyException;

interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
}

class CompoundStatement implements IStatement {
    IStatement first;
    IStatement second;

    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return state;
    }
}

class PrintStatement implements IStatement {
    Expression exp;

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    ProgramState execute(ProgramState state) throws MyException {
        // todo
        return state;
    }

    // todo
}

class AssignmentStatement implements IStatement {
    String id;
    Expression exp;

    public String toString() {
        return id + "=" + exp.toString();
    }

    ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        MyIDictionary<String, Value> symTable = state.getSymbolTable();

        if (symTable.isDefined(id)) {
            Value val = exp.evaluate(symTable);
            Type typeId = (symTable.lookup(id)).getType();
            if (val.getType().equals(typeId))
                symTable.update(id, val);
            else
                throw new MyException(
                        "Declared type of variable " + id + " and type of the assigned expression do not match");
        } else
            throw new MyException("The used variable " + id + " was not declared before");
        return state;
    }
    // ...
}

class IfStatement implements IStatement {
    Expression exp;
    IStatement thenS;
    IStatement elseS;
    // ...

    IfStatement(Expression _exp, IStatement _then, IStatement _else) {
        exp = _exp;
        thenS = _then;
        elseS = _else;
    }

    public String toString() {
        return "(IF(" + exp.toString() + ") THEN (" + thenS.toString() + ") ELSE (" + elseS.toString() + "))";
    }

    ProgramState execute(ProgramState state) throws MyException {
        // todo
        return state;
    }
}

class VarDeclStatement implements IStatement {
    String name;
    Type type;

    // ...
}

class NopStatement implements IStatement {
    // ...
}

interface Type {
}

class IntType implements Type {
    public boolean equals(Object another) {
        if (another instanceof IntType)
            return true;
        else
            return false;
    }

    public String toString() {
        return "int";
    }
}

class BoolType implements Type {
    public boolean equals(Object another) {
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }

    public String toString() {
        return "bool";
    }
}

interface Value {
    Type getType();
}

class IntValue implements Value {
    int val;

    IntValue(int v) {
        val = v;
    }

    int getVal() {
        return val;
    }

    public String toString() {
        // ...
        return "";
    }

    public Type getType() {
        return new IntType();
    }
}

class BoolValue implements Value {
    boolean val;

    BoolValue(boolean v) {
        val = v;
    }

    boolean getVal() {
        return val;
    }

    public String toString() {
        // ...
        return "";
    }

    public Type getType() {
        return new BoolType();
    }
}

interface Expression {
    Value eval(MyIDictionary<String, Value> table) throws MyException;
}

class ArithmeticExpression implements Expression {
    Expression e1;
    Expression e2;
    int op; // 1 - plus, 2 - minus, 3 - star, 4 - divide

    Value eval(MyIDictionary<String, Value> table) throws MyException {
        Value v1, v2;
        v1 = e1.eval(table);

        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(table);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();

                if (op == 1)
                    return new IntValue(n1 + n2);
                if (op == 2)
                    return new IntValue(n1 - n2);
                if (op == 3)
                    return new IntValue(n1 * n2);
                if (op == 4)
                    if (n2 == 0)
                        throw new MyException("Divison by zero");
                    else
                        return new IntValue(n1 / n2);
            } else
                throw new MyException("Second operand is not an integer");
        } else
            throw new MyException("First operand is not an integer");
    }
}

class ValueExpression implements Expression {
    Value e;

    Value eval(MyIDictionary<String, Value> table) throws MyException {
        return e;
    }
}

class LogicExpression implements Expression {
    Expression e1;
    Expression e2;
    int op;

    Value eval(MyIDictionary<String, Value> table) throws MyException {
        // todo
    }
}

class VariableExpression implements Expression {
    String id;

    Value eval(MyIDictionary<String, Value> table) throws MyException {
        return table.lookup(id);
    }
}

interface MyIStack<T> {
    T pop();

    void push(T v);
}

class MyStack<T> implements MyIStack<T> {
    CollectionType<T> stack;

    public T pop() {
        // todo
    }

    public void push(T v) {
        // todo
    }
}

interface MyIDictionary<T, V> {

}

interface MyIList<T> {

}

class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, Value> symbolTable;
    MyIList<Value> out;

    IStatement originalProgram; // optional but good

    ProgramState(MyIStack<IStatement> _exeStack, MyIDictionary<String, Value> _symbolTable, MyIList<Value> _out,
            IStatement _originalProgram) {
        exeStack = _exeStack;
        symbolTable = _symbolTable;
        out = _out;
        originalProgram = deepCopy(_originalProgram); // recreate the entire original prg
        _exeStack.push(_originalProgram);
    }
    // ... override tostring, setters getters for all fields
}
package model.statements;

import MyException.InvalidOperation;
import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.dataStructures.myHeap.MyIHeap;
import model.expressions.Expression;
import model.programState.ProgramState;
import model.types.Type;
import model.values.Value;

public class AssignmentStatement implements IStatement {
    String id;
    Expression exp;

    /*
     * Constructor
     */
    public AssignmentStatement(String _id, Expression _exp) {
        this.id = _id;
        this.exp = _exp;
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "AssignmentStatement{" + id + " = " + exp.toString() + "}";
    }

    /*
     * Overriding execute, the method inherited from implementing the interface
     * IStatement
     * We check if the variable we want to assign something to is defined
     * If it is, we evaluate the expression whose result we want to assign to our
     * variable
     * If the type of the result is the one we declared previously we continue and
     * update the variable
     * Otherwise we raise exceptions
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException, InvalidOperation {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (!symTable.isDefined(id)) {
            throw new MyException("The used variable " + id + " was not declared before");
        }

        Value val = exp.eval(symTable, heap);
        Type typeId = (symTable.lookup(id)).getType();

        if (!val.getType().equals(typeId)) {
            throw new MyException(
                    "Declared type of variable " + id + " and type of the assigned expression do not match");
        }

        symTable.update(id, val);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(this.id, this.exp);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = this.exp.typecheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        } else {
            throw new MyException("Assignment: RHS and LHS diff types!");
        }
    }
}

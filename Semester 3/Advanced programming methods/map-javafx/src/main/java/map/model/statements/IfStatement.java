package map.model.statements;

import map.MyException.InvalidOperation;
import map.MyException.MyException;
import map.model.dataStructures.myDictionary.MyIDictionary;
import map.model.dataStructures.myHeap.MyIHeap;
import map.model.expressions.*;
import map.model.programState.*;
import map.model.types.BoolType;
import map.model.types.Type;
import map.model.values.BoolValue;
import map.model.values.Value;

public class IfStatement implements IStatement {
    Expression exp;
    IStatement thenS;
    IStatement elseS;

    /*
     * Constructor
     */
    IfStatement(Expression _exp, IStatement _then, IStatement _else) {
        exp = _exp;
        thenS = _then;
        elseS = _else;
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "IF{" + this.exp.toString() + "} THEN {" + this.thenS.toString() + "} ELSE {" + this.elseS.toString()
                + "}}";
    }

    /*
     * Overriding execute(), the method inherited from the implemented interface
     * IStatement.
     * If the statement in the if is true we push the first branch, otherwise the
     * second branch(else) on the execution stack
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException, InvalidOperation {
        MyIDictionary<String, Value> dict = state.getSymbolTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(dict, heap);

        if (!val.getType().equals(new BoolType())) {
            throw new MyException("Type mismatch");
        }

        BoolValue v = (BoolValue) val;
        if (v.getVal())
            state.getExeStack().push(thenS);
        else
            state.getExeStack().push(elseS);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = this.exp.typecheck(typeEnv);
        if (!expType.equals(new BoolType())) {
            throw new MyException("IfStmt: Exp doesn't evaluate to bool");
        }
        this.thenS.typecheck(typeEnv.deepCopy());
        this.elseS.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
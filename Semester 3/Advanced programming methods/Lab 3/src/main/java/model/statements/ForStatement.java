package model.statements;

import MyException.MyException;
import model.dataStructures.myDictionary.MyIDictionary;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.expressions.RelationalOperator;
import model.expressions.VariableExpression;
import model.programState.ProgramState;
import model.types.IntType;
import model.types.Type;

public class ForStatement implements IStatement {
    private Expression exp1, exp2, exp3;
    private IStatement statementInFor;
    private String v;

    public ForStatement(String _v, Expression _exp1, Expression _exp2, Expression _exp3, IStatement _statementInFor) {
        this.v = _v;
        this.exp1 = _exp1;
        this.exp2 = _exp2;
        this.exp3 = _exp3;
        this.statementInFor = _statementInFor;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement statementInWhile = new CompoundStatement(new AssignmentStatement(this.v, exp1),
                new WhileStatement(
                        new RelationalExpression(new VariableExpression(this.v), this.exp2, RelationalOperator.L),
                        new CompoundStatement(statementInFor, new AssignmentStatement(this.v, exp3))));
        state.getExeStack().push(statementInWhile);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeOfV = new VariableExpression(this.v).typecheck(typeEnv);
        Type expression1Type = this.exp1.typecheck(typeEnv);
        Type expression2Type = this.exp2.typecheck(typeEnv);
        Type expression3Type = this.exp3.typecheck(typeEnv);

        if (!expression1Type.equals(new IntType()) || !expression2Type.equals(new IntType())
                || !expression3Type.equals(new IntType()) || !typeOfV.equals(new IntType())) {
            throw new MyException("ForStmt: one of the expressions do not evaluate to int");
        }

        return typeEnv;
    }

    @Override
    public String toString() {
        return "ForStatement(" + this.exp1 + "; " + this.exp2 + "; " + this.exp3;
    }

    @Override
    public ForStatement deepCopy() {
        return new ForStatement(this.v, this.exp1.deepCopy(), this.exp2.deepCopy(), this.exp3.deepCopy(),
                this.statementInFor);
    }
}

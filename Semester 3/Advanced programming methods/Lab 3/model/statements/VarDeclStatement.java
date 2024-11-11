package model.statements;

import model.dataStructures.myDictionary.*;
import model.programState.*;
import model.values.*;
import model.types.*;
import MyException.MyException;

public class VarDeclStatement implements IStatement {
    String name;
    Type type;

    /*
     * Constructor for variable declaration statement
     */
    public VarDeclStatement(String _name, Type _type) {
        name = _name;
        type = _type;
    }

    /*
     * Getter for the name of the variable we are declaring
     */
    public String getName() {
        return this.name;
    }

    /*
     * Getter for the type of the variable we are declaring
     */
    public Type getType() {
        return this.type;
    }

    /*
     * Setter for the name of the variable we are declaring
     */
    public void setName(String _name) {
        this.name = _name;
    }

    /*
     * Setter for the type of the variable we are declaring
     */
    public void setType(Type _type) {
        this.type = _type;
    }

    /*
     * Implementation of the inherited method execute from the IStatement interface
     * If variable is already declared we raise an exception
     * Otherwise we just add it in the symbol table
     */
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> dict = state.getSymbolTable();

        if (dict.isDefined(this.name)) {
            throw new MyException("Variable is already defined!");
        } else {
            dict.put(this.name, this.type.defaultValue());
        }

        return state;
    }

    /*
     * Overriding toString()
     */
    @Override
    public String toString() {
        return "VarDeclStatement{" + "name = '" + this.name + "', type = " + this.type + "}";
    }

    @Override
    public VarDeclStatement deepCopy() {
        return new VarDeclStatement(this.name, this.type);
    }
}

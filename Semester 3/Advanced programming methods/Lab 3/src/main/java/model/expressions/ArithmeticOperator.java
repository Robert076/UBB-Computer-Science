package model.expressions;

/*
 * This is to make code cleaner so we do not have
 * int op = 1 // plus
 * int op = 2 // subtraction
 * etc..
 * 
 * We use this by ArithmeticOperator.PLUS, etc.
 */
public enum ArithmeticOperator {
    PLUS, MINUS, MULTIPLY, DIVIDE
}
package booleanoo;

import booleanoo.operators.Not;

/**
 * A unary negation.
 */
public class Negation extends UnaryExpression{
    public Negation(BooleanExpression op){
        super(new Not(), op);
    }
}

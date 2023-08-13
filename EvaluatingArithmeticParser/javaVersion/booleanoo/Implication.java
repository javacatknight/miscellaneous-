package booleanoo;

import booleanoo.operators.Implies;

/**
 * A binary implication of BooleanExpression's.
 */
public class Implication extends BinaryExpression{
    public Implication (BooleanExpression left, BooleanExpression right){
        super(left, new Implies(), right);
    } 
}

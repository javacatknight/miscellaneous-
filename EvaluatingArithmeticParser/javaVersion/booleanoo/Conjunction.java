package booleanoo;

import booleanoo.operators.And;

/**
 * A binary conjunction of BooleanExpression's.
 */
public class Conjunction extends BinaryExpression {
    public Conjunction (BooleanExpression left, BooleanExpression right){
        super(left, new And(), right);
    }

   
}

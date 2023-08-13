package booleanoo;

import booleanoo.operators.Or;

/**
 * A binary disjunction of BooleanExpression's.
 */
public class Disjunction {
    public Disjunction (BooleanExpression left, BooleanExpression right){
        super(left, new Or(), right);
    }  
}

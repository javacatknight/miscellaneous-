package booleanoo;

/**
 * A binary if and only if of BooleanExpression's.
 */
public class IffExpression {
    public IffExpression (BooleanExpression left, BooleanExpression right) {
        super(left, new Iff(), right);   
    }
}

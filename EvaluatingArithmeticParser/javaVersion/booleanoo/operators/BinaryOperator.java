package booleanoo.operators;

/**
 * A binary boolean operator.
 */
public interface BinaryOperator extends BooleanOperator{
    public Boolean apply (Boolean left, Boolean right);
}

package booleanoo.operators;

/**
 * A unary boolean operator.
 */
public interface UnaryOperator extends BooleanOperator{
  public Boolean apply (Boolean operand);

}

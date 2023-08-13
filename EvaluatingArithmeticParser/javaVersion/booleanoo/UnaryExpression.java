package booleanoo;

import java.util.Map;

import booleanoo.operators.UnaryOperator;

/**
 * A unary boolean expression.
 */
abstract class UnaryExpression extends BooleanExpression {
  private UnaryOperator operator;
  private BooleanExpression operand; 

  public UnaryExpression(UnaryOperator operator, BooleanExpression operand){
    this.operator = operator;
    this.operand = operand;
  }

@Override
public Boolean evaluate (Map<String,Boolean> context){
  return operator.apply(operand.evaluate(context));
} 
@Override
public BooleanExpression simplifyOnce (Map<String,Boolean> context){
  if (this instanceof Negation) {
    //(not(not x)) -> x
    if (operand instanceof Negation){
      Negation x = (Negation) operand;
      return x.getOperand().simplifyOnce(context);
    }
    //else: (not x) -> returns either (not x) or (!x)
    BooleanExpression b = operand.simplifyOnce(context); //b is varaiable or booleanvalue
      if (b instanceof BooleanValue) {
        return new BooleanValue (operator.apply(b.evaluate(context)));
      } else if (b instanceof Variable) {
        return new Negation(b);
      }
    }
    return this; //won't happen but useful to extend code
  }
  

  @Override
  public boolean equals(Object other) {
    return other != null && other.getClass().equals(this.getClass())
        && ((UnaryExpression) other).operator.equals(operator)
        && ((UnaryExpression) other).operand.equals(operand);
  }

  @Override
  public String toString() {
    return String.format("(%s %s)", operator, operand.toString());
  }

  protected final BooleanExpression getOperand() {
    return this.operand;
  }
  protected final UnaryOperator getOperator () {
    return this.operator;
  }
}

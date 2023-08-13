package booleanoo;

import java.util.Map;

import booleanoo.operators.BinaryOperator;

/**
 * A binary boolean expression.
 */

 //This class is abstract because the methods of simplifyonce are implemented by iffExpression and... etc.
public abstract class BinaryExpression extends BooleanExpression{
  private BinaryOperator operator;
  private BooleanExpression left;
  private BooleanExpression right;

  public BinaryExpression(BooleanExpression left, BinaryOperator operator, BooleanExpression right){
    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  @Override
  public Boolean evaluate (Map<String,Boolean> context) {
    return this.operator.apply(left.evaluate(context), right.evaluate(context));
  } 

  @Override
  public BooleanExpression simplifyOnce (Map<String, Boolean> context) {
    BooleanExpression left = this.getLeft().simplifyOnce(context);
    BooleanExpression right = this.getRight().simplifyOnce(context);

    BooleanValue t = new BooleanValue (true);
    BooleanValue f = new BooleanValue (false);

      if (this instanceof Conjunction){
        if (left.equals(t)){
          return right;
        } else if (right.equals(t)){
          return left;
        } else if (left.equals(f) || right.equals(f)) {
          return f;
        } 
        //suppose And (a, b) ... you'd think there are extra cases like (t and t) but there isn't!
        //the only special case, where (a, b) both don't get evaluated further -> we leave to our ultimate end case: just return And(a, b) as it is

      } else if (this instanceof Disjunction) {
        if (left.equals(f)) {
          return right;
        } else if (right.equals(f)){
          return left;
        } else if (left.equals(t) || right.equals(t)) {
          return t;
        } 
      } else if (this instanceof Implication) {
        if (left.equals(right) || left.equals(f) || right.equals(t))
          return t;
        if (left.equals(t))
          return right;
        if (right.equals(f))
          return new Negation(left);

      } else if (this instanceof IffExpression){
        if (left.equals(right))
          return t;
        if (left.equals(t))
          return right;
        if (right.equals(t))
          return left;
        if (left.equals(f))
          return new Negation(right);
        if (right.equals(f))
          return new Negation(left);
      }
      return this;
    }

  @Override
  public boolean equals(Object other) {
    return other != null && other.getClass().equals(this.getClass())
        && ((BinaryExpression) other).operator.equals(operator)
        && ((BinaryExpression) other).left.equals(left)
        && ((BinaryExpression) other).right.equals(right);
  }

  @Override
  public String toString() {
    return String.format("(%s %s %s)", left, operator, right);
  }

  protected final BooleanExpression getLeft(){
    return this.left;
  }
  protected final BooleanExpression getRight(){
    return this.right;
  }
  protected final BinaryOperator getOperator(){
    return this.operator;
  }

}

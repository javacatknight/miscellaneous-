package booleanoofunc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * A binary boolean expression.
 */
public abstract class BinaryExpression extends BooleanExpression {
  private BooleanExpression left;
  private BooleanExpression right;
  private BinaryOperator<Boolean> operator;
  private BiFunction<List<BooleanExpression>, Map<String, Boolean>, BooleanExpression> simplifier;

  public BinaryExpression(BinaryOperator<Boolean> operator,
      BooleanExpression left,
      BooleanExpression right,
      BiFunction<List<BooleanExpression>, Map<String, Boolean>, BooleanExpression> simplifier) {
    this.operator = operator;
    this.left = left;
    this.right = right;
    this.simplifier = simplifier;
  }

  public Boolean evaluate (Map<String,Boolean> context){
    return operator.apply(left.evaluate(context), right.evaluate(context));
  }

  @Override
  public BooleanExpression simplifyOnce (Map<String,Boolean> context) {
    List <BooleanExpression> lst = new ArrayList <BooleanExpression> ();
    lst.add(left);
    lst.add(right);
    return simplifier.apply(lst, context);
  }

  @Override
  public String toString() {
    return String.format("(%s %s %s)", left, toStringOp(), right);
  }

  public abstract String toStringOp();

  protected final BooleanExpression getLeft() {
    return left;
  }

  protected final BooleanExpression getRight() {
    return right;
  }

  @Override
  public boolean equals(Object other) {
    return other != null && other.getClass().equals(this.getClass())
        && ((BinaryExpression) other).left.equals(left)
        && ((BinaryExpression) other).right.equals(right);
  }
}







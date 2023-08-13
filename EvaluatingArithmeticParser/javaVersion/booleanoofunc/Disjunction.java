package booleanoofunc;

import java.util.List;
import java.util.Map;

/**
 * A binary disjunction of BooleanExpression's.
 */
public class Disjunction extends BinaryExpression {
    /**
   * Creates a new "left or right".
   *
   * @param left of Disjunction
   * @param right of Disjunction
   */

  public Disjunction(BooleanExpression left, BooleanExpression right) {
    super((x, y) -> x||y, left, right, Disjunction::simplifyOr);
  }

  private static BooleanExpression simplifyOr(List <BooleanExpression> lst,
  Map<String, Boolean> context) {

  BooleanExpression left = lst.get(0).simplifyOnce(context);
  BooleanExpression right = lst.get(1).simplifyOnce(context);
  BooleanValue t = new BooleanValue (true);
  BooleanValue f = new BooleanValue (false);

  if (left.equals(f)) {
    return right;
  } else if (right.equals(f)){
    return left;
  } else if (left.equals(t) || right.equals(t)) {
    return t;
  } else {
    return new Disjunction(left, right);
  }
}

  @Override
  public String toStringOp() {
    return Constants.OR;
  }
}

package booleanoofunc;

import java.util.List;
import java.util.Map;

/**
 * A binary implication of BooleanExpression's.
 */
public class Implication extends BinaryExpression {

  @Override
  public String toStringOp() {
    return Constants.IMPLIES;
  }

  public Implication(BooleanExpression left, BooleanExpression right) {
    super((x, y) -> (!x || y), left, right, Implication::simplifyImplies);
  }

  private static BooleanExpression simplifyImplies (List <BooleanExpression>lst,
  Map<String, Boolean> context)
  {

    BooleanExpression left = lst.get(0).simplifyOnce(context);
    BooleanExpression right = lst.get(1).simplifyOnce(context);
    BooleanValue t = new BooleanValue(true);
    BooleanValue f = new BooleanValue(false);

    if (left.equals(right) || left.equals(f) || right.equals(t))
      return t;
    if (left.equals(t))
      return right;
    if (right.equals(f))
      return new Negation(left);
    else
      return new Implication(left, right);
  }
}

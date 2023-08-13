package booleanoofunc;

import java.util.*;

/**
 * A binary if and only if of BooleanExpression's.
 */
public class IffExpression extends BinaryExpression {
  @Override
  public String toStringOp() {
    return Constants.IFF;
  }

  public IffExpression(BooleanExpression left, BooleanExpression right) {
    super((x, y) -> ((x && y) || (!x && !y)), left, right, IffExpression::simplifyIff);
  }

  private static BooleanExpression simplifyIff(List<BooleanExpression> lst,
  Map<String, Boolean> context) {

  BooleanExpression left = lst.get(0).simplifyOnce(context);
  BooleanExpression right = lst.get(1).simplifyOnce(context);
  BooleanValue t = new BooleanValue (true);
  BooleanValue f = new BooleanValue (false);


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
    else
      return new IffExpression(left, right);
  }
}
package booleanoofunc;

import java.util.List;
import java.util.Map;

/**
 * A binary conjunction of BooleanExpression's.
 */
public class Conjunction extends BinaryExpression{
  
public Conjunction(BooleanExpression left, BooleanExpression right) {
  super((x, y) -> x && y, left, right, Conjunction::simplifyAnd);
}

private static BooleanExpression simplifyAnd(List <BooleanExpression> lst,
    Map<String, Boolean> context) {

    BooleanExpression left = lst.get(0).simplifyOnce(context);
    BooleanExpression right = lst.get(1).simplifyOnce(context);
    BooleanValue t = new BooleanValue (true);
    BooleanValue f = new BooleanValue (false);

    if (left.equals(t)){
      return right;
    } else if (right.equals(t)){
      return left;
    } else if (left.equals(f) || right.equals(f)) {
      return f;
    } else {
      return new Conjunction (left, right);
    }
  }

  @Override
  public String toStringOp() {
    return Constants.AND;
  }

}

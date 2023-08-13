package booleanoofunc;

import java.util.Map;

/**
 * A unary negation.
 */
public class Negation extends UnaryExpression {

  /**
   * Creates a new "not operand".
   *
   * @param operand the operand of this Negation
   */
  public Negation(BooleanExpression operand) {
    super(x -> !x, operand, Negation::simplifyNot);
  } //operator, operand, simplifynot


  private static BooleanExpression simplifyNot(BooleanExpression expr,
    Map<String, Boolean> context) {

    BooleanExpression simplifiedOperand = expr.simplifyOnce(context);
    //new Negation(operand).simplifyOnce = simplifier.apply(operand, context)
    //(not(not x).simplifyOnce = simplifier.apply((not x), context)
    //simplifiedExpr = not x
      if (simplifiedOperand instanceof Negation){
          Negation returnable = (Negation) simplifiedOperand;
          return returnable.getOperand().simplifyOnce(context);
        }
        //else: 
        if (simplifiedOperand instanceof BooleanValue) {
          return new BooleanValue ((new Negation(simplifiedOperand)).evaluate(context));
        } else if (simplifiedOperand instanceof Variable) {
          return new Negation(simplifiedOperand);
        }
      
      //won't happen but
      return null;
    }
    

      


  @Override
  public String toStringOp() {
    return Constants.NOT;
  }
}

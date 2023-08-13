package booleanoofunc;

import java.util.Map;

/**
 * A boolean-values variable.
 */
public class Variable extends BooleanExpression {
  String id;
 
 public Variable (String id){
  this.id = id;
 }

@Override
public Boolean evaluate (Map<String,Boolean> context) throws UnassignedVariableException {
  if (context.containsKey(this.id) == false) {
    throw new UnassignedVariableException("Uhoh, you have unassigned variable " + this.toString());
  } else {
    return context.get(this.id);
  }
}

@Override
 public BooleanExpression simplifyOnce (Map<String,Boolean> context){
  if (context.containsKey(this.id))
  return new BooleanValue (context.get(this.id));
return this;
}

  @Override
  public boolean equals(Object other) {
    return other != null && other.getClass().equals(Variable.class)
        && id.equals(((Variable) other).id);
  }

  @Override
  public String toString() {
    return id;
  }
}

package booleanoo;

import java.util.*;

/**
 * A boolean value: true or false.
 */
public class BooleanValue extends BooleanExpression {
  Boolean value;

  //Constructor
  public BooleanValue (Boolean val){
    this.value = val;
  }

@Override
 public Boolean evaluate (Map<String,Boolean> context){
  return value;
 }
 
@Override
public BooleanExpression simplifyOnce (Map<String,Boolean> context){
  return this;
}
  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public boolean equals(Object other) {
    return other != null && other.getClass().equals(BooleanValue.class)
        && ((BooleanValue) other).value.equals(value);
  }
}

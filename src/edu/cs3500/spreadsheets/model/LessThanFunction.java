package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a LessThan operator that evaluates if the first arg is less than the second.
 */
public class LessThanFunction implements FunctionOperation {

  @Override
  public Value apply(ArrayList<Value> argVals) {
    ArrayList<Double> doubleArgs = new ArrayList<>();
    for (Value argVal : argVals) {
      doubleArgs.add(argVal.accept(new ReturnsZeroDefaultValueVisitor()));
    }
    if (doubleArgs.size() != 2) {
      throw new IllegalArgumentException("The less than function can only compare 2 digits.");
    }
    return new BooleanValue(doubleArgs.get(0) < doubleArgs.get(1));
  }

}


package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

public class SqrtFunction implements FunctionOperation {
  @Override
  public Value apply(ArrayList<Value> argVals) {
    ArrayList<Double> doubleArgs = new ArrayList<>();
    for (Value argVal : argVals) {
      doubleArgs.add(argVal.accept(new ReturnsOneDefaultValueVisitor()));
    }
    if (doubleArgs.size() != 1) {
      throw new IllegalArgumentException("The sqrt function can only take in one digit.");
    }

    return new DoubleValue(Math.sqrt(doubleArgs.get(0)));
  }
}

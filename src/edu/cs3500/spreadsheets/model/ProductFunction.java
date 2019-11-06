package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a Product operator that evaluates the product of a list of args.
 */
public class ProductFunction implements FunctionOperation {

  @Override
  public Value apply(ArrayList<Value> argVals) {
    ArrayList<Double> doubleArgs = new ArrayList<>();
    for (Value argVal : argVals) {
      doubleArgs.add(argVal.accept(new ReturnsOneDefaultValueVisitor()));
    }
    double prod = 1.0;
    for (Double doubleArg : doubleArgs) {
      prod = prod * doubleArg;
    }
    return new DoubleValue(prod);
  }

}

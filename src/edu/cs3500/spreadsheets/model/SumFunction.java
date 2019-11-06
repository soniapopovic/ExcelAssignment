package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

public class SumFunction implements FunctionOperation {

  @Override
  public Value apply(ArrayList<Value> argVals) {
    ArrayList<Double> doubleArgs = new ArrayList<>();
    for (Value argVal : argVals) {
      doubleArgs.add(argVal.accept(new ReturnsZeroDefaultValueVisitor()));
    }
    Double sum = 0.0;
    for (Double doubleArg : doubleArgs) {
      sum += doubleArg;
    }
    return new DoubleValue(sum);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SumFunction sumFunc = (SumFunction) o;
    //return sumFunc.function.equals(function)
    //TODO: finish this!
    return true;
  }
}

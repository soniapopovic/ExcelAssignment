package edu.cs3500.spreadsheets.model;

/**
 * Visits a value and returns 0 if it is not a DoubleValue, else it returns the
 */
public class ReturnsZeroDefaultValueVisitor implements IOperationValueVisitor<Double> {

  @Override
  public Double visitBoolean(BooleanValue booleanValue, boolean val) {
    return 0.0;
  }

  @Override
  public Double visitDouble(DoubleValue doubleValue, double val) {
    return val;
  }

  @Override
  public Double visitString(StringValue stringValue, String val) {
    return 0.0;
  }
}

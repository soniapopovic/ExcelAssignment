package edu.cs3500.spreadsheets.model;

/**
 * A ValueVisitor applies functions over Values. * @param <R> The return type of this function
 */
public interface IOperationValueVisitor<R> {

  /**
   * Gets the correct value of this Boolean for a given Function.
   *
   * @return R representing a boolean's value.
   */
  R visitBoolean(BooleanValue booleanValue, boolean val);

  /**
   * Gets the correct value of this Double for a given Function.
   *
   * @return R representing a double's value.
   */
  R visitDouble(DoubleValue doubleValue, double val);

  /**
   * Gets the correct value of this String for a given Function.
   *
   * @return R representing a String's value.
   */
  R visitString(StringValue stringValue, String val);

}

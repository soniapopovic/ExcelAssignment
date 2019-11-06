package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Represents a Value.
 */
public interface Value extends Formula {


  <R> R accept(IOperationValueVisitor<R> visitor);

  /**
   * Gets the Boolean given the BooleanValue.
   * @return a Boolean
   */
  Boolean getBooleanValue();

  /**
   * Gets the String given the StringValue.
   * @return a String
   */
  String getStringValue();

  /**
   * Gets the Double value given the DoubleValue.
   * @return a Double
   */
  Double getDoubleValue();


}

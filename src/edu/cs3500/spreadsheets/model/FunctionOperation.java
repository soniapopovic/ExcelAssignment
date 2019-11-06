package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Applies a function to the list of arguments passed to a Function Formula.
 */
public interface FunctionOperation {

  /**
   * Applies a function to the given set of arguments.
   */
  public Value apply(ArrayList<Value> argVals);
}

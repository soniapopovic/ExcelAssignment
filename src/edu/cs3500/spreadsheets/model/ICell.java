package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * an interface that represents any type of Cell
 */
public interface ICell {

  void setHasBeenChecked(boolean b);

  boolean getHasBeenChecked();

  Value evaluateCell();

  void setValue(Value value);

  /**
   * Gets the field value of the ICell.
   *
   * @return a Value
   */
  Value getValue();

  /**
   * Checks whether this specific ICell depends on a cycle.
   *
   * @return a boolean
   */
  boolean dependsOnCycle();

  /**
   * Retrieves the field formula of this ICell.
   *
   * @return a Formula
   */
  Formula getFormula();

  /**
   * Find the Coord at which this ICell is located in the spreadsheet.
   *
   * @return a Coord
   */
  Coord getCoord();

  /**
   * Checks whether the ICell is a cycle.
   *
   * @return a boolean
   */
  //boolean isACycle();
}


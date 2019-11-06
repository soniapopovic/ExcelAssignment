package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * Represents the contents of a cell.
 */
public interface Formula {

  /**
   * Evaluates the Value of the a Formula.
   *
   * @return a Value representing the result of this formula.
   */
  Value evaluate();

  /**
   * Flattens the inputs to be evaluated in the formula to a list of formulas.
   */
  ArrayList<Formula> flatten();


  /**
   * helper to determine whether the Cell depends on a cycle.
   *
   * @param seen the Stack<Coord> that contains the Coords already seen </Coord>
   * @return a boolean
   */
  boolean dependsOnCycleHelp(Stack<Coord> seen);

  /**
   * Gets this cell's referenced cells.
   */
  ArrayList<Coord> getReferences();

}

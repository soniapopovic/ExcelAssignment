package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Represents a Cell in a spreadsheet.
 */
public class Cell extends AbstractCell {
  private Value value;
  private Formula formula;
  private boolean hasBeenChecked;


  /**
   * creates a Cell given a Formula and a Coord.
   *
   * @param formula the Formula to be evaluated
   * @param c       the Coord
   */
  public Cell(Formula formula, Coord c) {
    this.location = c;
    this.formula = formula;
    this.hasBeenChecked = false;
  }


  @Override
  public void setHasBeenChecked(boolean b) {
    this.hasBeenChecked = b;
  }

  @Override
  public boolean getHasBeenChecked() {
    return this.hasBeenChecked;
  }

  @Override
  public void setValue(Value value) {
    this.value = value;
  }


  //TODO make this private? Why should it be public
  public Value evaluateCell() {
    //if (!this.hasBeenChecked) {
      if (this.dependsOnCycle()) {
        this.value = new DoubleValue(0.0);
        //this.setHasBeenChecked(true);
        return this.value;
      }
      else {
        //this.setHasBeenChecked(true);
        this.value = this.formula.evaluate();
        return this.value;
      }
    //} else {
    //  return this.value;
    //}
  }

  @Override
  public Value getValue() {
    if (this.value == null) {
      this.value = this.evaluateCell();
      return this.value;
    } else {
      return this.value;
    }
  }

  /**
   * Retrieves the Formula from the Cell.
   *
   * @return a Formula
   */
  public Formula getFormula() {
    return this.formula;
  }

  /**
   * Checks if the Cell depends on a cycle.
   *
   * @return a Boolean
   */
  @Override
  public boolean dependsOnCycle() {
    //Map<Coord, Boolean> seen = new HashMap<>();
    Stack<Coord> seen = new Stack<>();
    //seen.put(this.getCoord(), true);
    seen.push(this.getCoord());
    //this.setHasBeenChecked(true);
    boolean result = formula.dependsOnCycleHelp(seen);
    return result;
  }

}

package edu.cs3500.spreadsheets.model;

/**
 * Represents an EmptyCell with no formula or Value.
 */
public class EmptyCell extends AbstractCell {

  private Value value;
  /**
   * Constructs an EmptyCell at a given Coord.
   *
   * @param c a Coord
   */
  public EmptyCell(Coord c) {
    this.location = c;
    this.value = new DoubleValue(0.0);
  }

  @Override
  public void setHasBeenChecked(boolean b) {

  }

  @Override
  public boolean getHasBeenChecked() {
    return true;
  }

  @Override
  public Value evaluateCell() {
    return this.value;
  }

  @Override
  public void setValue(Value value) {

  }

  @Override
  public boolean dependsOnCycle() {
    return false;
  }

  @Override
  public Formula getFormula() {
    return new DoubleValue(0.0);
  }

  @Override
  public Value getValue() {
    if (this.value == null) {
      return this.evaluateCell();
    } else {
      return this.value;
    }
  }

}

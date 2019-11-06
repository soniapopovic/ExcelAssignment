package edu.cs3500.spreadsheets.model;

/**
 * an abstract class of an ICell.
 */
abstract class AbstractCell implements ICell {

  protected Coord location;
  protected Value value;

  /**
   * Gets the Value of an ICell.
   *
   * @return
   */
  @Override
  public Value getValue() {
    if (this.value == null) {
      return this.evaluateCell();
    } else {
      return this.value;
    }
  }


  /**
   * Gets the Coordinate of this ICell in the spreadsheet.
   *
   * @return the Coord
   */
  @Override
  public Coord getCoord() {
    return this.location;
  }
}

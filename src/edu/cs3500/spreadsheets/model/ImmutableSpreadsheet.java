package edu.cs3500.spreadsheets.model;

import java.util.Map;

/**
 * A mock worksheet that can be used to return a Formula in the worksheet without exposing it's
 * contents.
 */
public final class ImmutableSpreadsheet {

  public Map<Coord, ICell> worksheet;

  /**
   * Creates a mock worksheet based on the given worksheet.
   */
  public ImmutableSpreadsheet(ISpreadsheet spreadsheet) {
    if (spreadsheet == null) {
      throw new IllegalArgumentException("Given spreadsheet cannot be null");
    }
    this.worksheet = spreadsheet.getWorksheet();
  }

  /**
   * Gets the cell at the given coordinate.
   */
  public ICell getCellAt(Coord coord) {
    return this.worksheet.get(coord);
  }

  /**
   * Retrieves the Formula at a given Coord.
   *
   * @param coord a Coord
   * @return a Formula
   */
  public Formula getFormulaAt(Coord coord) {
    return this.worksheet.get(coord).getFormula();
  }
}



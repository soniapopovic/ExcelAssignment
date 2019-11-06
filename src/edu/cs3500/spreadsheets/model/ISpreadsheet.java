package edu.cs3500.spreadsheets.model;

import java.util.List;
import java.util.Map;

/**
 * Represents a spreadsheet.
 */
public interface ISpreadsheet<C> {

  /**
   * Gets the cell at the given coordinate.
   */
  //TODO change this and a lot more takes in int row and int col
  C getCellAt(int col, int row);

  /**
   * Creates a Cell at the given coordinate.
   */
  //TODO these all take in String contents instead of Formula
  void createCellAt(int col, int row, String contents);

  /**
   * Updates the Cell at the given coordinate.
   */
  void updateCellAt(int col, int row, String contents);

  /**
   * Gets the evaluated cell.
   */
  Value getEvaluatedCell(int col, int row);

  /**
   * Deletes the Cell at the given coordinate.
   */
  void deleteCellAt(int col, int row);

  /**
   * Returns a deep copy of this spreadsheet's worksheet.
   */
  Map<Coord, C> getWorksheet();

  /**
   * Gets the raw String contents that were given to an ICell.
   *
   * @param col the column of the ICell in the spreadsheet
   * @param row the row of the ICell in the spreadsheet
   * @return a String
   */
  String getRawContents(int col, int row);

  /**
   * Gets a List of error messages for each ICell that s not valid.
   *
   * @return a List<String> of messages </String>
   */
  List<String> getErrors();
}

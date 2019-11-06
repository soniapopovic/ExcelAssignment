package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.sexp.GetFormulaVisitor;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

/**
 * Builds a Spreadsheet.
 */

//TODO make the error messages.

public class SSWorksheetBuilder implements WorksheetBuilder<Spreadsheet> {

  private final Spreadsheet spreadsheet = new Spreadsheet();

  @Override
  public WorksheetBuilder<Spreadsheet> createCell(int col, int row, String contents) {
    spreadsheet.createCellAt(col, row, contents);
    return this;
  }

  @Override
  public Spreadsheet createWorksheet() {
    return spreadsheet;
  }
}

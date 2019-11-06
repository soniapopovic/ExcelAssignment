package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.sexp.GetFormulaVisitor;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;


/**
 * Represents a Spreadsheet.
 */
public class Spreadsheet implements ISpreadsheet<ICell> {

  private Map<Coord, ICell> worksheet = new HashMap<>();
  private Map<Coord, String> rawContents = new HashMap<>();

  @Override
  public ICell getCellAt(int col, int row) {
    Coord coord = new Coord(col, row);
    return this.worksheet.get(coord);

  }


  @Override
  public void createCellAt(int col, int row, String contents) {
    Coord c = new Coord(col, row);
    Formula formula = parseContents(contents);
    Cell cell = new Cell(formula, c);
    worksheet.put(c, cell);
    cell.setHasBeenChecked(false);
    cell.setValue(null);
    rawContents.put(new Coord(col, row), contents);

  }

  @Override
  public void updateCellAt(int col, int row, String contents) {
   this.createCellAt(col, row, contents);
  }

  @Override
  public Value getEvaluatedCell(int col, int row) {
    return worksheet.get(new Coord(col, row)).getValue();
  }

  @Override
  public void deleteCellAt(int col, int row) {
    worksheet.remove(new Coord(col, row));
    rawContents.remove(new Coord(col, row));

  }

  /**
   * Parses through the String to retrieve a Formula.
   *
   * @param contents a String
   * @return a Formula
   */
  private Formula parseContents(String contents) {
    Sexp sexp;
    if (contents == null) {
      throw new IllegalArgumentException("The contents cannot be null.");
    }
    Character c = contents.charAt(0);
    if (c.equals('=')) {
      sexp = Parser.parse(contents.substring(1));
    } else {
      sexp = Parser.parse(contents);
    }
    Formula formula = sexp.accept(new GetFormulaVisitor(this));

    ArrayList<Coord> refs = formula.getReferences();

    for (Coord ref : refs) {
      if (worksheet.get(ref) == null) {
        worksheet.put(ref, new EmptyCell(ref));
      }
    }

    return formula;
  }


  @Override
  public Map<Coord, ICell> getWorksheet() {
    return this.worksheet;
  }

  @Override
  public String getRawContents(int col, int row) {
    Coord c = new Coord(col, row);
    return rawContents.get(c);
  }

  @Override
  public List<String> getErrors() {
    List<String> ret = new ArrayList<>();
    for (Map.Entry<Coord, ICell> pair : worksheet.entrySet()) {
      ICell c = pair.getValue();
      Coord coord = pair.getKey();
      if (c.dependsOnCycle()) {
        ret.add("The cell " + coord.toString() + " depends on a cycle.");
      }
    }
    return ret;
  }

}

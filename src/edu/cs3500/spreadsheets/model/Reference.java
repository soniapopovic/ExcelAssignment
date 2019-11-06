package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * Represents a Cell that references other cells.
 */
public class Reference implements Formula {

  private final ImmutableSpreadsheet worksheet;
  private final ArrayList<Coord> referencedFormulas = new ArrayList<Coord>();

  /**
   * Creates a Reference given the worksheet and a cell.
   *
   * @param worksheet    the worksheet we are working with.
   * @param oneReference the start and finish coordinates of a range of cells.
   */
  public Reference(ImmutableSpreadsheet worksheet, Coord oneReference) {
    this.worksheet = worksheet;

    this.referencedFormulas.add(oneReference);


  }

  /**
   * Creates a Reference given the worksheet and two cells marking the start and finish of a range
   * of cells.
   *
   * @param worksheet         the worksheet we are working with.
   * @param multipleReference the start and finish coordinates of a range of cells.
   */
  public Reference(ImmutableSpreadsheet worksheet, ArrayList<Coord> multipleReference) {
    this.worksheet = worksheet;
    if (multipleReference.size() > 2) {
      throw new IllegalArgumentException("Not a valid reference.");
    }
    if (!this.isValidMultipleReference(multipleReference)) {
      throw new IllegalArgumentException("The second referenced cell must come after the first.");
    }
    int col1 = multipleReference.get(0).col;
    int row1 = multipleReference.get(0).row;
    int col2 = multipleReference.get(1).col;
    int row2 = multipleReference.get(1).row;
    for (int i = col1; i <= col2; i++) {
      for (int j = row1; j <= row2; j++) {
        referencedFormulas.add(new Coord(i, j));
      }
    }
  }

  /**
   * Checks whether the two cells form a valid Reference range.
   *
   * @return a boolean indicating whether the second cell comes after the first.
   */
  private boolean isValidMultipleReference(ArrayList<Coord> multipleReference) {
    int col1 = multipleReference.get(0).col;
    int row1 = multipleReference.get(0).row;
    int col2 = multipleReference.get(1).col;
    int row2 = multipleReference.get(1).row;
    if (col1 == col2 && row1 == row2) {
      return false;
    } else {
      return (col1 <= col2 && row1 <= row2);
    }
  }

  @Override
  public Value evaluate() {
    if (referencedFormulas.size() == 1) {
        return this.worksheet.getCellAt(referencedFormulas.get(0)).getValue();
    } else {
      throw new IllegalArgumentException("You cannot evaluate a reference cell that references"
              + "more than one cell.");
    }
  }

  @Override
  public ArrayList<Formula> flatten() {
    ArrayList<Formula> formulas = new ArrayList<>();
    for (Coord coord : referencedFormulas) {
      formulas.add(worksheet.getCellAt(coord).getValue());
    }
    return formulas;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reference ref = (Reference) o;
    return ref.worksheet.equals(worksheet) && ref.referencedFormulas.equals(referencedFormulas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(referencedFormulas) + Objects.hash(worksheet);
  }

  @Override
  public ArrayList<Coord> getReferences() {
    return this.referencedFormulas;
  }


  //TODO it is falling into the null category but then it is automatically assuming it is a cycle.
  //TODO change Stack to a Map instead.
  @Override
  public boolean dependsOnCycleHelp(Stack<Coord> seen) {
    for (Coord c : referencedFormulas) {
      //if(seen.get(c) == null) {
        //seen.put(c, false);
      //} else
        if (seen.contains(c) /*seen.get(c) == true*/) {
        return true;
      } else {
        //seen.put(c, true);
        seen.push(c);
        boolean ret = worksheet.getCellAt(c).getFormula().dependsOnCycleHelp(seen);
        if (ret) {
          return ret;
        }
        //seen.remove(c);
        seen.pop();
      }
    }
    return false;
  }
}

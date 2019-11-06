package edu.cs3500.spreadsheets.sexp;

import edu.cs3500.spreadsheets.model.BooleanValue;
import edu.cs3500.spreadsheets.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.DoubleValue;
import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.Function;
import edu.cs3500.spreadsheets.model.FunctionOperation;
import edu.cs3500.spreadsheets.model.ISpreadsheet;
import edu.cs3500.spreadsheets.model.ImmutableSpreadsheet;
import edu.cs3500.spreadsheets.model.LessThanFunction;
import edu.cs3500.spreadsheets.model.ProductFunction;
import edu.cs3500.spreadsheets.model.Reference;
import edu.cs3500.spreadsheets.model.SqrtFunction;
import edu.cs3500.spreadsheets.model.StringValue;
import edu.cs3500.spreadsheets.model.SumFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Gets the formula from an SExpr.
 */
public class GetFormulaVisitor implements SexpVisitor<Formula> {

  private final ISpreadsheet<Cell> spreadsheet;

  public GetFormulaVisitor(ISpreadsheet spreadsheet) {
    this.spreadsheet = spreadsheet;
  }

  @Override
  public Formula visitBoolean(boolean b) {
    return new BooleanValue(b);
  }

  @Override
  public Formula visitNumber(double d) {
    return new DoubleValue(d);
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    Sexp operator = l.get(0);
    FunctionOperation functionOperation = getFunctionObject(operator);
    List<Sexp> args = l.subList(1, l.size());
    ArrayList<Formula> formulaArgs = new ArrayList<>();
    for (Sexp arg : args) {
      formulaArgs.add(arg.accept(new GetFormulaVisitor(spreadsheet)));
    }
    return new Function(functionOperation, formulaArgs);
  }

  /**
   * Returns Function object corresponding to corresponding operator.
   *
   * @param operator the Sexpr corresponding to where the operator should be.
   * @return a FunctionObject.
   */
  private FunctionOperation getFunctionObject(Sexp operator) {
    //TODO : this equals is not working... I don't know how to fix this?
    if (operator.equals(new SSymbol("SUM"))) {
      return new SumFunction();
    } else if (operator.equals(new SSymbol("PRODUCT"))) {
      return new ProductFunction();
    } else if (operator.equals(new SSymbol("<"))) {
      return new LessThanFunction();
    } else if (operator.equals(new SSymbol("SQRT"))) {
      return new SqrtFunction();
    } else {
      throw new IllegalArgumentException("Not a valid excel function.");
    }
  }


  @Override
  public Formula visitSymbol(String s) {
    if (s.contains(":")) {
      String[] references = s.split(":");
      Coord ref1, ref2;
      if (isValidReference(references[0]) && isValidReference(references[1])) {
        ref1 = convertToCoord(references[0]);
        ref2 = convertToCoord(references[1]);
        ArrayList<Coord> coords = new ArrayList<>();
        coords.add(ref1);
        coords.add(ref2);
        return new Reference(new ImmutableSpreadsheet(spreadsheet), coords);
      } else {
        throw new IllegalArgumentException("This is not a valid range of references.");
      }
    } else if (isValidReference(s)) {
      return new Reference(new ImmutableSpreadsheet(spreadsheet), convertToCoord(s));
    } else {
      throw new IllegalArgumentException("This is not a valid reference input.");
    }
  }

  private boolean isValidReference(String ref) {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < ref.length(); i++) {
      Character c = ref.charAt(i);
      if (Character.isLetter(c)) {
        s.append(c);
      } else {
        break;
      }
    }
    for (int i = s.length(); i < ref.length(); i++) {
      Character n = ref.charAt(i);
      if (!Character.isDigit(n)) {
        return false;
      }
    }
    return true;
  }

  private Coord convertToCoord(String ref) {
    int i = 0;
    while (Character.isLetter(ref.charAt(i))) {
      i++;
    }
    String col = ref.substring(0, i);
    int row = Integer.valueOf(ref.substring(i));
    return new Coord(Coord.colNameToIndex(col), row);
  }

  @Override
  public Formula visitString(String s) {
    return new StringValue(s);
  }
}

package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * Represents a Cell which calls a function.
 */
public class Function implements Formula {

  private FunctionOperation function;
  //TODO change args back to private
  public ArrayList<Formula> args = new ArrayList<>();

  /**
   * Creates a function based on the given function object and coordinates of cells referencing
   * formulas to be passed as args.
   *
   * @param function the FunctionOperation to be applied
   * @param args     an ArrayList<Formula> for which the function is to be applied on </Formula>
   */
  public Function(FunctionOperation function, ArrayList<Formula> args) {
    this.function = function;
    this.args = args;
  }


  @Override
  public Value evaluate() {
    ArrayList<Value> argVals = new ArrayList<>();
    ArrayList<Formula> arg;
    for (Formula formula : args) {
      arg = formula.flatten();
      for (Formula f : arg) {
        argVals.add(f.evaluate());
//        if (!(f instanceof Value)) {
//          argVals.add(f.evaluate());
//        } else {
//          argVals.add((Value) f);
//        }
      }
    }
    return function.apply(argVals);
  }

  @Override
  public ArrayList<Formula> flatten() {
    ArrayList<Formula> functionList = new ArrayList<>();
    functionList.add(this);
    return functionList;
  }



  @Override
  public boolean dependsOnCycleHelp(Stack<Coord> seen) {
    for (Formula arg : args) {
      if (arg.dependsOnCycleHelp(seen)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ArrayList<Coord> getReferences() {
    ArrayList<Coord> refs = new ArrayList<>();
    for (Formula arg: args) {
      for (Coord ref : arg.getReferences()) {
        refs.add(ref);
      }
    }
    return refs;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Function func = (Function) o;
    return func.function.equals(function) && func.args.equals(args);
  }

  @Override
  public int hashCode() {
    return Objects.hash(function) + Objects.hash(args);
  }

}


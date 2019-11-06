package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * Represents a Double Value.
 */
public class DoubleValue implements Value {

  private final Double doubleVal;

  /**
   * Creates a DoubleValue containing the given double.
   */
  public DoubleValue(Double doubleVal) {
    this.doubleVal = doubleVal;
  }



  @Override
  public <R> R accept(IOperationValueVisitor<R> visitor) {
    return visitor.visitDouble(this, this.doubleVal);
  }

  @Override
  public Boolean getBooleanValue() {
    return null;
  }

  @Override
  public String getStringValue() {
    return null;
  }

  @Override
  public Double getDoubleValue() {
    return this.doubleVal;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    DoubleValue doubleValue = (DoubleValue) other;
    return Double.compare(doubleValue.doubleVal, doubleVal) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(doubleVal);
  }

  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public ArrayList<Formula> flatten() {
    ArrayList<Formula> flattenedFormula = new ArrayList<>();
    flattenedFormula.add(this);
    return flattenedFormula;
  }


  @Override
  public boolean dependsOnCycleHelp(Stack<Coord> seen) {
    return false;
  }

  @Override
  public ArrayList<Coord> getReferences() {
    return new ArrayList<>();
  }

  @Override
  public String toString() {
    return String.format("%f", this.doubleVal);
  }

}

package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * Represents a Boolean Value.
 */
public class BooleanValue implements Value {

  private boolean booleanVal;

  /**
   * Create a Boolean Value with the given boolean as it's value.
   *
   * @param booleanVal the contents of the Boolean Value.
   */
  public BooleanValue(boolean booleanVal) {
    this.booleanVal = booleanVal;
  }


  @Override
  public <R> R accept(IOperationValueVisitor<R> visitor) {
    return visitor.visitBoolean(this, this.booleanVal);
  }

  @Override
  public Boolean getBooleanValue() {
    return this.booleanVal;
  }

  @Override
  public String getStringValue() {
    return null;
  }

  @Override
  public Double getDoubleValue() {
    return null;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    BooleanValue booleanValue = (BooleanValue) other;
    return Boolean.compare(booleanValue.booleanVal, booleanVal) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(booleanVal);
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
    return Boolean.toString(this.booleanVal);
  }
}

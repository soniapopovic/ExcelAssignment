package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * Represents a String Value.
 */
public class StringValue implements Value {

  private final String stringVal;

  /**
   * Creates a StringValue whose value is the given String.
   */
  public StringValue(String stringVal) {
    this.stringVal = stringVal;
  }


  @Override
  public <R> R accept(IOperationValueVisitor<R> visitor) {
    return visitor.visitString(this, this.stringVal);
  }

  @Override
  public Boolean getBooleanValue() {
    return null;
  }

  @Override
  public String getStringValue() {
    return this.stringVal;
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
    StringValue stringValue = (StringValue) other;
    return stringVal.equals(stringValue.stringVal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stringVal);
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
  public ArrayList<Coord> getReferences() {
    return new ArrayList<>();
  }

  @Override
  public boolean dependsOnCycleHelp(Stack<Coord> seen) {
    return false;
  }

  @Override
  public String toString() {
    return this.stringVal;
  }

}

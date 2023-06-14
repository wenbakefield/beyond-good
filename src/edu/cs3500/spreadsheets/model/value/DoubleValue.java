package edu.cs3500.spreadsheets.model.value;

import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;

/**
 * Represents an implementation of ValueContent that evaluates to a Double.
 */
public class DoubleValue implements ValueContent<Double> {
  private double value;

  public DoubleValue(double value) {
    this.value = value;
  }

  @Override
  public <R> R accept(ContentVisitor<R> cellVisitor) {
    return cellVisitor.visitDoubleValue(this);
  }

  @Override
  public ValueContent getValueContent() {
    return this;
  }

  @Override
  public Double getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return Double.toString(this.value);
  }
}
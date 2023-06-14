package edu.cs3500.spreadsheets.model.value;

import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;

/**
 * Represents an implementation of ValueContent that evaluates to a Boolean.
 */
public class BooleanValue implements ValueContent<Boolean> {
  private boolean value;

  public BooleanValue(boolean value) {
    this.value = value;
  }

  @Override
  public <R> R accept(ContentVisitor<R> cellVisitor) {
    return cellVisitor.visitBooleanValue(this);
  }

  @Override
  public ValueContent getValueContent() {
    return this;
  }

  @Override
  public Boolean getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return Boolean.toString(this.value);
  }
}
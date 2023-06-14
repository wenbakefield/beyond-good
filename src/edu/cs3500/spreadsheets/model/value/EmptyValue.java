package edu.cs3500.spreadsheets.model.value;

import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;

/**
 * Represents an implementation of ValueContent that is empty.
 */
public class EmptyValue implements ValueContent {

  @Override
  public <R> R accept(ContentVisitor<R> contentVisitor) {
    return contentVisitor.visitEmptyValue(this);
  }

  @Override
  public ValueContent getValueContent() {
    return this;
  }

  @Override
  public Object getValue() throws IllegalEmptyValueException {
    throw new IllegalEmptyValueException("Empty cell does not have a value!");
  }

  @Override
  public String toString() {
    return "";
  }

}
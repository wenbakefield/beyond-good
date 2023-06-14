package edu.cs3500.spreadsheets.model.value;

import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;

/**
 * Represents an implementation of ValueContent that evaluates to a String.
 */
public class StringValue implements ValueContent<String> {
  private String value;

  public StringValue(String value) {
    this.value = value;
  }

  @Override
  public <R> R accept(ContentVisitor<R> contentVisitor) {
    return contentVisitor.visitStringValue(this);
  }

  @Override
  public ValueContent getValueContent() {
    return this;
  }

  @Override
  public String getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return "\"" + this.value + "\"";
  }

}
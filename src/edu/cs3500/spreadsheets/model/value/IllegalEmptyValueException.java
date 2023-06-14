package edu.cs3500.spreadsheets.model.value;

/**
 * Represents an exception that is thrown if a value is pulled from an EmptyValue.
 */
public class IllegalEmptyValueException extends IllegalStateException {
  IllegalEmptyValueException(String e) {
    super(e);
  }
}
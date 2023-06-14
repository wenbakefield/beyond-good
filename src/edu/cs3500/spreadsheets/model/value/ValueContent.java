package edu.cs3500.spreadsheets.model.value;

import edu.cs3500.spreadsheets.model.cell.CellContent;

/**
 * Represents an extension of Cell that will hold a double, boolean, or String based on the specific
 * ValueContent cell's type parameter.
 */
public interface ValueContent<R> extends CellContent {

  /**
   * Gets the value that the ValueContent contains.
   *
   * @return the appropriate value of type R
   */
  R getValue() throws IllegalEmptyValueException;
}
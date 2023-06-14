package edu.cs3500.spreadsheets.model.cell;

import edu.cs3500.spreadsheets.model.value.ValueContent;
import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;

/**
 * An interface representing the content of a Cell, which is the data the Cell holds.
 * A CellContent can be a FunctionContent, a ReferenceContentOld, or a ValueContent.
 */
public interface CellContent {

  /**
   * Allows CellContent to accept a visitor, which will cause the visitor to "visit" it and process
   * the CellContent appropriately.
   * @param visitor the ContentVisitor which is being accepted
   * @param <R>     the data which the ContentVisitor is meant to return
   * @return        the appropriate data return when the ContentVisitor processes the CellContent
   */
  <R> R accept(ContentVisitor<R> visitor);

  /**
   * Gets the ValueContent equivalent to the CellContent's evaluation.
   * @return the appropriate ValueContent the CellContent evaluates to
   */
  ValueContent getValueContent();
}

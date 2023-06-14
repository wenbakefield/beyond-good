package edu.cs3500.spreadsheets.model.visitor;

import edu.cs3500.spreadsheets.model.cell.FunctionContent;
import edu.cs3500.spreadsheets.model.cell.ReferenceContent;
import edu.cs3500.spreadsheets.model.value.BooleanValue;
import edu.cs3500.spreadsheets.model.value.DoubleValue;
import edu.cs3500.spreadsheets.model.value.EmptyValue;
import edu.cs3500.spreadsheets.model.value.IllegalEmptyValueException;
import edu.cs3500.spreadsheets.model.value.StringValue;

/**
 * Visits each of the types of CellContents and grabs the necessary data from them,
 * depending on the implementation of ContentVisitor.
 * @param <R> the data type that the visitor returns from the passed CellContent
 */
public interface ContentVisitor<R> {

  /**
   * Passes this ContentVisitor to the given BooleanValue and returns
   * the appropriate value of type R.
   * @param booleanValue the BooleanValue to be visited
   * @return the evaluated value of type R
   */
  R visitBooleanValue(BooleanValue booleanValue);

  /**
   * Passes this ContentVisitor to the given DoubleValue and returns
   * the appropriate value of type R.
   * @param doubleValue the DoubleValue to be visited
   * @return the evaluated value of type R
   */
  R visitDoubleValue(DoubleValue doubleValue);

  /**
   * Passes this ContentVisitor to the given StringValue and returns
   * the appropriate value of type R.
   * @param stringValue the StringValue to be visited
   * @return the evaluated value of type R
   */
  R visitStringValue(StringValue stringValue);

  /**
   * Passes this ContentVisitor to the given EmptyValue and returns
   * the appropriate value of type R.
   * @param emptyValue the EmptyValue to be visited
   * @return the evaluated value of type R
   */
  R visitEmptyValue(EmptyValue emptyValue);

  /**
   * Passes this ContentVisitor to the given FunctionContent and returns
   * the appropriate value of type R.
   * @param functionContent the FunctionContent to be visited
   * @return the evaluated value of type R
   */
  R visitFunctionContent(FunctionContent functionContent) throws IllegalEmptyValueException;

  /**
   * Passes this ContentVisitor to the given ReferenceContent and returns
   * the appropriate value of type R.
   * @param referenceContent the ReferenceContent to be visited
   * @return the evaluated value of type R
   */
  R visitReferenceContent(ReferenceContent referenceContent);
}
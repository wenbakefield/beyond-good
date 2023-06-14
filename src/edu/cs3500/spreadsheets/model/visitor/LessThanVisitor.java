package edu.cs3500.spreadsheets.model.visitor;

import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.cell.CellContent;
import edu.cs3500.spreadsheets.model.cell.FunctionContent;
import edu.cs3500.spreadsheets.model.cell.ReferenceContent;
import edu.cs3500.spreadsheets.model.value.BooleanValue;
import edu.cs3500.spreadsheets.model.value.DoubleValue;
import edu.cs3500.spreadsheets.model.value.EmptyValue;
import edu.cs3500.spreadsheets.model.value.StringValue;

/**
 * Represents an implementation of ContentVisitor that returns an appropriate BooleanValue for each
 * CellContent it visits. Determines whether the first extracted double is less than the second.
 */
public class LessThanVisitor implements ContentVisitor<BooleanValue> {

  @Override
  public BooleanValue visitBooleanValue(BooleanValue booleanValue) {
    throw new IllegalArgumentException("Cannot compare a single boolean value!");
  }

  @Override
  public BooleanValue visitDoubleValue(DoubleValue doubleValue) {
    throw new IllegalArgumentException("Cannot compare a single double value!");
  }

  @Override
  public BooleanValue visitStringValue(StringValue stringValue) {
    throw new IllegalArgumentException("Cannot compare a single string value!");
  }

  @Override
  public BooleanValue visitEmptyValue(EmptyValue emptyValue) {
    throw new IllegalArgumentException("Cannot compare a single empty value!");
  }

  @Override
  public BooleanValue visitFunctionContent(FunctionContent functionContent) {
    List<CellContent> cellContents = functionContent.getArguments();
    if (cellContents.size() != 2) {
      throw new IllegalArgumentException("Can only compare two values!");
    } else {
      try {
        return new BooleanValue((double) cellContents.get(0).getValueContent().getValue()
                < (double) cellContents.get(1).getValueContent().getValue());
      } catch (ClassCastException e) {
        throw new IllegalArgumentException("Can only compare numerical values!");
      }
    }
  }

  @Override
  public BooleanValue visitReferenceContent(ReferenceContent referenceContent) {
    throw new IllegalArgumentException("Cannot compare a single reference!");
  }

  @Override
  public String toString() {
    return "<";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    } else {
      return this == o;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

}
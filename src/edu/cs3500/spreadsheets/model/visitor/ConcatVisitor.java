package edu.cs3500.spreadsheets.model.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.cell.CellContent;
import edu.cs3500.spreadsheets.model.cell.FunctionContent;
import edu.cs3500.spreadsheets.model.cell.ReferenceContent;
import edu.cs3500.spreadsheets.model.value.BooleanValue;
import edu.cs3500.spreadsheets.model.value.DoubleValue;
import edu.cs3500.spreadsheets.model.value.EmptyValue;
import edu.cs3500.spreadsheets.model.value.IllegalEmptyValueException;
import edu.cs3500.spreadsheets.model.value.StringValue;
import edu.cs3500.spreadsheets.model.value.ValueContent;

/**
 * Represents an implementation of ContentVisitor that returns an appropriate StringValue for each
 * CellContent it visits. Concatenates the extracted values separated by comma to a String.
 */
public class ConcatVisitor implements ContentVisitor<StringValue> {

  @Override
  public StringValue visitFunctionContent(FunctionContent functionContent) {
    StringBuilder result = new StringBuilder();
    List<ValueContent> values = new ArrayList<>();
    for (CellContent content : functionContent.getArguments()) {
      values.addAll(content.accept(new EvaluateVisitor()));
    }
    for (ValueContent value : values) {
      try {
        result.append(", ").append(value.accept(this).getValue());
      } catch (IllegalEmptyValueException e) {
        break;
      }
    }
    return new StringValue(result.toString().substring(2));
  }

  @Override
  public StringValue visitReferenceContent(ReferenceContent referenceContent) {
    StringBuilder output = new StringBuilder();
    for (CellContent content : referenceContent.getContents()) {
      output.append(", ").append(content.accept(this).getValue());
    }
    return new StringValue(output.toString().substring(2));
  }

  @Override
  public StringValue visitDoubleValue(DoubleValue doubleValue) {
    return new StringValue(doubleValue.toString());
  }

  @Override
  public StringValue visitStringValue(StringValue stringValue) {
    return stringValue;
  }

  @Override
  public StringValue visitBooleanValue(BooleanValue booleanValue) {
    return new StringValue(booleanValue.toString());
  }

  @Override
  public StringValue visitEmptyValue(EmptyValue emptyValue) {
    return new StringValue("");
  }

  @Override
  public String toString() {
    return "CONCAT";
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

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
 * Represents an implementation of ContentVisitor that returns an appropriate DoubleValue for each
 * CellContent it visits. Multiplies the extracted doubles.
 */
public class ProdVisitor implements ContentVisitor<DoubleValue> {

  @Override
  public DoubleValue visitBooleanValue(BooleanValue booleanValue) {
    return new DoubleValue(1.0);
  }

  @Override
  public DoubleValue visitDoubleValue(DoubleValue doubleValue) {
    return doubleValue;
  }

  @Override
  public DoubleValue visitStringValue(StringValue stringValue) {
    return new DoubleValue(1.0);
  }

  @Override
  public DoubleValue visitEmptyValue(EmptyValue emptyValue) {
    return new DoubleValue(1.0);
  }

  @Override
  public DoubleValue visitFunctionContent(FunctionContent functionContent) {
    Double result = 1.0;
    List<ValueContent> valueContents = new ArrayList<>();
    for (CellContent content : functionContent.getArguments()) {
      valueContents.addAll(content.accept(new EvaluateVisitor()));
    }
    for (ValueContent value : valueContents) {
      try {
        result = result * value.accept(this).getValue();
      } catch (IllegalEmptyValueException e) {
        break;
      }
    }
    return new DoubleValue(result);
  }

  @Override
  public DoubleValue visitReferenceContent(ReferenceContent referenceContent) {
    double output = 1.0;
    for (CellContent content : referenceContent.getContents()) {
      output = output * content.accept(this).getValue();
    }
    return new DoubleValue(output);
  }

  @Override
  public String toString() {
    return "PRODUCT";
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
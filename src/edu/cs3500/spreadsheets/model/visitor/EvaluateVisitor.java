package edu.cs3500.spreadsheets.model.visitor;

import java.util.ArrayList;
import java.util.List;

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
 * Represents an implementation of ContentVisitor that returns an appropriate list of ValueContent
 * for each CellContent it visits.
 */
public class EvaluateVisitor implements ContentVisitor<List<ValueContent>> {

  @Override
  public List<ValueContent> visitBooleanValue(BooleanValue booleanValue) {
    List<ValueContent> output = new ArrayList<>();
    output.add(booleanValue);
    return output;
  }

  @Override
  public List<ValueContent> visitDoubleValue(DoubleValue doubleValue) {
    List<ValueContent> output = new ArrayList<>();
    output.add(doubleValue);
    return output;
  }

  @Override
  public List<ValueContent> visitStringValue(StringValue stringValue) {
    List<ValueContent> output = new ArrayList<>();
    output.add(stringValue);
    return output;
  }

  @Override
  public List<ValueContent> visitEmptyValue(EmptyValue emptyValue) {
    List<ValueContent> output = new ArrayList<>();
    output.add(emptyValue);
    return output;
  }

  @Override
  public List<ValueContent> visitFunctionContent(FunctionContent functionContent) {
    List<ValueContent> output = new ArrayList<>();
    ValueContent valueContent = functionContent.getValueContent();
    output.add(valueContent);
    return output;
  }

  @Override
  public List<ValueContent> visitReferenceContent(ReferenceContent referenceContent) {
    List<ValueContent> output = new ArrayList<>();
    if (referenceContent.getContents().size() == 1) {
      try {
        output.add(referenceContent.getContents().get(0).getValueContent());
      } catch (IllegalEmptyValueException e) {
        return output;
      }
    } else {
      for (CellContent content : referenceContent.getContents()) {
        try {
          output.add(content.getValueContent());
        } catch (IllegalEmptyValueException e) {
          break;
        }
      }
    }
    return output;
  }
}
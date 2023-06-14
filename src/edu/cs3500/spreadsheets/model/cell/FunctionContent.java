package edu.cs3500.spreadsheets.model.cell;

import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.value.ValueContent;
import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;

/**
 * Represents an implementation of CellContent.  This CellContent represents a function that can be
 * executed by a Cell on the Cells it references as arguments.
 */
public class FunctionContent implements CellContent {
  private final ContentVisitor function;
  private List<CellContent> arguments;

  /**
   * Constructs a FunctionContent using the given arguments.
   *
   * @param function  the corresponding CellVisitor which handles functionality
   * @param arguments the arguments of the function
   */
  public FunctionContent(ContentVisitor function, List<CellContent> arguments) {
    if (arguments == null) {
      throw new IllegalArgumentException("Function arguments cannot be null!");
    }
    this.function = function;
    this.arguments = arguments;
  }

  @Override
  public <R> R accept(ContentVisitor<R> visitor) {
    return visitor.visitFunctionContent(this);
  }

  @Override
  public ValueContent getValueContent() {
    return (ValueContent) this.function.visitFunctionContent(this);
  }

  public List<CellContent> getArguments() {
    return this.arguments;
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (CellContent content : this.arguments) {
      output.append(" ").append(content.toString());
    }
    return "(" + this.function.toString() + output + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FunctionContent that = (FunctionContent) o;
    return Objects.equals(this.function, that.function);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.function, this.arguments);
  }
}

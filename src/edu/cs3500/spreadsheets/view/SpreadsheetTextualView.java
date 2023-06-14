package edu.cs3500.spreadsheets.view;

import java.io.IOException;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.cell.Cell;

/**
 * An implementation of SpreadsheetView which renders a SpreadsheetModel of Cells
 * as a text file of Strings.
 */
public class SpreadsheetTextualView implements SpreadsheetView {
  private final SpreadsheetModel<Cell> model;
  private Appendable out;

  /**
   * Constructs a SpreadsheetTextualView using the given arguments.
   * @param model the SpreadsheetModel being represented
   * @param out   the text file to append the String representation to
   */
  public SpreadsheetTextualView(SpreadsheetModel model, Appendable out) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    this.model = model;
    this.out = out;
  }

  @Override
  public void render() throws IOException {
    StringBuilder output = new StringBuilder();
    for (Cell cell : model.getCells()) {
      output.append(cell.toString()).append("\n");
    }
    this.out.append(output.toString());
  }
}
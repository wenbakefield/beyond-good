package edu.cs3500.spreadsheets.provider.adapter;

import java.util.HashMap;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;

/**
 * Adapter class to represent a SpreadsheetModel as a Worksheet model for the provider view.
 * Redirects provider methods to the SpreadsheetModel interface.
 */
public class WorksheetModel {
  private SpreadsheetModel model;

  /**
   * Constructs a WorksheetModel adapter with the given SpreadsheetModel.
   * @param model the model to be converted
   */
  public WorksheetModel(SpreadsheetModel model) {
    this.model = model;
  }

  /**
   * Returns the grid of Coords mapped to their respective Cells.
   * @return the HashMap of Coords and Cells
   */
  public HashMap getSheet() {
    return this.model.getSheet();
  }

  /**
   * Returns the evaluated contents of the cell at the given coord.
   * @param coord the location of the cell in the grid
   * @return the evaluated contents of the cell
   */
  public String getValue(Coord coord) {
    String output = this.model.getEvaluatedCell(coord);
    if (output.startsWith("\"")) {
      output = this.model.getEvaluatedCell(coord).replaceAll("^\"|\"$", "");
    } else {
      output = this.model.getEvaluatedCell(coord);
    }
    return output;
  }

  /**
   * Updates the contents of the cell at the given coord with the given contents.
   * @param clickedPos the location of the cell in the grid
   * @param text the new contents of the cell
   */
  public void setCell(Coord clickedPos, String text) {
    if (text.startsWith("(") || text.equals("true") || text.equals("false") || isNumeric(text)) {
      this.model.modifyCell(clickedPos, text);
    } else {
      this.model.modifyCell(clickedPos, "\"" + text + "\"");
    }
  }

  /**
   * Returns the raw CellContent of the cell at the given coord as a string.
   * @param coord the location of the cell in the grid.
   * @return the string of the CellContent of the cell at the given coord.
   */
  public String readCell(Coord coord) {
    return this.model.getCellContent(coord).toString();
  }

  private boolean isNumeric(String strNum) {
    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    if (strNum == null) {
      return false;
    }
    return pattern.matcher(strNum).matches();
  }
}
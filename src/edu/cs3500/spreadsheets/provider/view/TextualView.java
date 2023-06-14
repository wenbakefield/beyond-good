package edu.cs3500.spreadsheets.provider.view;

import java.io.IOException;
import java.util.HashMap;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.adapter.Content;
import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;


/**
 * An object representing the textual view of the worksheet.
 */
public class TextualView implements IView {
  private Appendable ap;
  private WorksheetModel m;

  /**
   * The constructor taking in an appendable to write to and the model to be displayed.
   * @param ap the output that the textual view should be written to.
   * @param m the model to be displayed.
   */
  public TextualView(Appendable ap, WorksheetModel m) {
    this.ap = ap;
    this.m = m;
  }

  /**
   * The function that outputs the textual view to the given appendable.
   */
  public void toFile() {
    HashMap<Coord, Content> spreadSheet = m.getSheet();

    for (Coord c: spreadSheet.keySet()) {
      String s = "";
      s += Coord.colIndexToName(c.col) + Integer.toString(c.row) + " ";
      s += m.getValue(c);
      try {
        ap.append(s + "\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

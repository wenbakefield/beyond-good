package edu.cs3500.spreadsheets.view;

import java.io.IOException;

/**
 * Represents a view of a Spreadsheet. A view can either be textual or visual. The textual view
 * is used to save the spreadsheet to a new file that can be read by the program. The visual
 * view is used to render an environment in which a user can view the spreadsheet.
 */
public interface SpreadsheetView {

  /**
   * Renders the spreadsheet.
   */
  void render() throws IOException;

}

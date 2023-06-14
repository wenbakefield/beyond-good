package edu.cs3500.spreadsheets.view;

import javax.swing.JFrame;

import edu.cs3500.spreadsheets.model.SpreadsheetModel;

/**
 * An implementation of SpreadsheetView which renders a SpreadsheetModel of cells in a graphical
 * window in which the spreadsheet can be viewed but not edited.
 */
public class SpreadsheetReadOnlyView extends JFrame implements SpreadsheetView {

  /**
   * Constructs a SpreadsheetReadOnlyView with the given model being used to render the spreadsheet.
   * @param model the viewable model to be rendered
   */
  public SpreadsheetReadOnlyView(SpreadsheetModel model) {
    SpreadsheetPanel panel = new SpreadsheetPanel(model, false);
    panel.setOpaque(true);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(panel);
    this.setTitle("Spreadsheet Viewer (Read-Only)");
  }

  @Override
  public void render() {
    this.pack();
    this.setVisible(true);
  }
}
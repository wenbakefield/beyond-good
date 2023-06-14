package edu.cs3500.spreadsheets.provider.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;

/**
 * Implementatino of a MouseListener for the JTable.
 */
public class MyMouseListener implements MouseListener {
  private WorksheetModel w;
  private GraphicView v;
  private int column;
  private int row;
  private String content;

  /**
   * Basic constructor that takes in a GraphicView.
   * @param v the graphic view to be applied to.
   */
  public MyMouseListener(GraphicView v) {
    this.w = v.table.getWorksheet();

    this.v = v;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    w = v.table.getWorksheet();
    this.row = v.table.rowAtPoint(e.getPoint()) + 1;
    this.column = v.table.columnAtPoint(e.getPoint()) + 1;
    if (row < 1 || column < 1) {
      return;
    }
    this.content = parseEquals(w.readCell(
            new Coord(this.column, this.row)));
    v.setTextField(content,  new Coord(this.column, this.row));

  }

  @Override
  public void mousePressed(MouseEvent e) {
    /**
     * Required override, but the functionality is not needed.
     */
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    /**
     * Required override, but the functionality is not needed.
     */
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    /**
     * Required override, but the functionality is not needed.
     */
  }

  @Override
  public void mouseExited(MouseEvent e) {
    /**
     * Required override, but the functionality is not needed.
     */
  }

  /**
   * Function to parse out the equals sign of the function being displayed.
   * @param s String to be parsed.
   * @return String without equals signs.
   */
  private String parseEquals(String s) {
    if (s.contains("=")) {
      return s.replace("=", "");
    }
    return s;
  }


}

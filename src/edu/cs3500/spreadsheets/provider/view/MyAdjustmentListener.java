package edu.cs3500.spreadsheets.provider.view;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.table.TableColumn;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;

/**
 * Our implementation of an Adjustment Listener to change what happens when the table.
 * is being scrolled through.
 */
public class MyAdjustmentListener implements AdjustmentListener {
  private int numColumn = 0;
  private int numRow = 0;
  GraphicView v;
  WorksheetModel w;


  /**
   * basic constructor for our adjustment listener class taking in the graphic view.
   * @param v the graphic view.
   */
  public MyAdjustmentListener(GraphicView v) {
    this.v = v;
    w = v.table.getWorksheet();
  }

  @Override
  public void adjustmentValueChanged(AdjustmentEvent e) {
    if (e.getValue() < numColumn * 0.85 && e.getValue() < numRow * 0.85) {
      return;
    }

    Adjustable source = e.getAdjustable();
    int orient = source.getOrientation();

    if (e.getValueIsAdjusting() && (orient == Adjustable.HORIZONTAL)
            && e.getValue() >= numColumn * 0.85) {
      numColumn = Math.max(numColumn, e.getValue());
      for (int i = 0; i <= 1; i++) {
        numColumn += 73;
        TableColumn c = new TableColumn();
        v.maxX += 1;
        c.setHeaderValue(Coord.colIndexToName(v.maxX));
        v.table.getColumnModel().addColumn(c);
        for (int y = 1; y < v.maxY; y++) {
          v.table.setValueAt("", i, v.maxX - 1);
        }
      }
    }

    if (e.getValueIsAdjusting() && (orient == Adjustable.VERTICAL)
            && e.getValue() >= numRow * 0.85) {
      numRow = Math.max(e.getValue(), numRow);
      for (int i = 0; i <= 1; i++) {
        v.rowHeader.getMyModel().addRow();
        v.rowHeader.ensureIndexIsVisible(v.maxY);
        numRow += 25;
        v.maxY ++;
        v.table.getMyModel().addRow();
      }
    }
  }


}

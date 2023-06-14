package edu.cs3500.spreadsheets.controller;

import javax.swing.table.AbstractTableModel;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.value.EmptyValue;

/**
 * An adapter class that provides controller functionality to the Editor view. Ports
 * methods from the model so that they are able to be used with JTable.
 */
public class SpreadsheetEditorAdapter extends AbstractTableModel {
  private SpreadsheetModel model;
  private boolean canEdit;

  /**
   * Constructs a SpreadsheetEditorAdapter with the given model and whether or not the model can
   * be edited as arguments.
   * @param model the model of the spreadsheet
   * @param canEdit whether or not the built JTable should be editable.
   */
  public SpreadsheetEditorAdapter(SpreadsheetModel model, boolean canEdit) {
    this.model = model;
    this.canEdit = canEdit;
  }

  @Override
  public int getRowCount() {
    return model.getNumRows();
  }

  @Override
  public int getColumnCount() {
    return model.getNumCols();
  }

  @Override
  public String getColumnName(int col) {
    return Coord.colIndexToName(col + 1);
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Coord coord = new Coord(columnIndex + 1, rowIndex + 1);
    if (model.getCoords().contains(coord)) {
      return model.getCellContent(coord);
    } else {
      return new EmptyValue();
    }
  }

  @Override
  public boolean isCellEditable(int row, int col) {
    if (canEdit) {
      return col >= 0;
    } else {
      return false;
    }
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    Coord coord = new Coord(col + 1, row + 1);
    model.modifyCell(coord, value.toString());
    fireTableCellUpdated(row, col);
  }

  /**
   * Adds an additional row to the model contained in this SpreadsheetEditorAdapter.
   */
  public void addRow() {
    Coord coord = new Coord(1, getRowCount() + 1);
    model.modifyCell(coord, "");
    fireTableRowsInserted(0, getRowCount());
  }

  /**
   * Retrieves the model from this SpreadsheetEditorAdapter.
   * @return the model
   */
  public SpreadsheetModel getModel() {
    return this.model;
  }

}

package edu.cs3500.spreadsheets.provider.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.adapter.Content;
import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;


/**
 * object to represent a table model for the graphical view.
 */
class MyTableModel extends AbstractTableModel implements TableModel {
  private List<String> columnNames;
  private List<List<Object>> data;

  /**
   * constructor to set the field of data and column headers.
   * @param data the data to be displayed within the cells.
   * @param header the column headers.
   */
  public MyTableModel(Object[][] data, String[] header) {
    this.data = new ArrayList<>();
    this.columnNames = new ArrayList<>();
    for (int i = 0; i < data.length; i++) {
      this.data.add(new ArrayList<Object>(header.length));
      for (int j = 0; j < header.length; j++) {
        this.data.get(i).addAll(Arrays.asList(data[i]));
      }
    }

    this.columnNames.addAll(Arrays.asList(header));

    //columnNames = header;
    //this.data = data;
  }

  /**
   * returns the number of columns in the spreadsheet.
   * @return the length of the column names.
   */
  public int getColumnCount() {
    return columnNames.size();
  }

  /**
   * returns the number of rows in the spreadsheet.
   * @return the length of the data nested array.
   */
  public int getRowCount() {
    return data.size();
  }

  /**
   * returns the column header at the given column index.
   * @param col the position of the column in the graph.
   * @return the name of the column.
   */
  public String getColumnName(int col) {
    return columnNames.get(col);
  }

  /**
   * retusn the value at the given row and column position.
   * @param row the y index of the cell.
   * @param col the x index of the cell.
   * @return the Object at the given indexes.
   */
  public Object getValueAt(int row, int col) {
    return data.get(row).get(col);
  }

  /**
   * retusn the class of the given column.
   * @param c the x position of the column.
   * @return the class type of that column.
   */
  public Class getColumnClass(int c) {
    return toString().getClass();
  }


  /**
   * checks if the given cell is editable.
   * @param row the y position of the cell.
   * @param col the x position of the cell.
   * @return whether or not the cell is editable.
   */
  public boolean isCellEditable(int row, int col) {
    return false;
    //return (col > 2);
  }

  /**
   * changes the value of the cell at the given x and y values.
   * @param value the value of the cell to be changed.
   * @param row the y position of the cell.
   * @param col the x position of the cell.
   */
  public void setValueAt(Object value, int row, int col) {
    data.get(row).set(col, value);
    fireTableCellUpdated(row, col);
  }

  /**
   * updates the table once a cell's value is changed.
   * @param w the worksheetmodel to be updated.
   */
  public void updateTable(WorksheetModel w) {
    Map<Coord, Content> contentMap = w.getSheet();
    for (Coord c: contentMap.keySet()) {
      this.setValueAt(w.getValue(c), c.row - 1, c.col - 1);

    }
  }

  /**
   * adds a row if the user scrolls past the limit.
   */
  public void addRow() {
    data.add(new ArrayList<>());
    for (int i = 0; i < columnNames.size(); i++) {
      data.get(data.size() - 1).add("");
    }
    fireTableRowsInserted(data.size(), data.size());

  }
}




/*
/**
 * object to represent a table model for the graphical view.
 *
class MyTableModel extends DefaultTableModel implements TableModel {
  private String[] columnNames;
  private Object[][] data;

  /**
   * constructor to set the field of data and column headers.
   * @param data the data to be displayed within the cells.
   * @param header the column headers.
   *
  public MyTableModel(Object[][] data, String[] header) {
    columnNames = header;
    this.data = data;
  }

  /**
   * returns the number of columns in the spreadsheet.
   * @return the length of the column names.
   *
  public int getColumnCount() {
    return columnNames.length;
  }

  /**
   * returns the number of rows in the spreadsheet.
   * @return the length of the data nested array.
   *
  public int getRowCount() {
    return data.length;
  }

  /**
   * returns the column header at the given column index.
   * @param col the position of the column in the graph.
   * @return the name of the column.
   *
  public String getColumnName(int col) {
    return columnNames[col];
  }

  /**
   * retusn the value at the given row and column position.
   * @param row the y index of the cell.
   * @param col the x index of the cell.
   * @return the Object at the given indexes.
   *
  public Object getValueAt(int row, int col) {
    return data[row][col];
  }

  /**
   * retusn the class of the given column.
   * @param c the x position of the column.
   * @return the class type of that column.
   *
  public Class getColumnClass(int c) {
    return toString().getClass();
  }


  /**
   * checks if the given cell is editable.
   * @param row the y position of the cell.
   * @param col the x position of the cell.
   * @return whether or not the cell is editable.
   *
  public boolean isCellEditable(int row, int col) {
    return false;
    //return (col > 2);
  }

  /**
   * changes the value of the cell at the given x and y values.
   * @param value the value of the cell to be changed.
   * @param row the y position of the cell.
   * @param col the x position of the cell.
   *
  public void setValueAt(Object value, int row, int col) {
    data[row][col] = value;
    fireTableCellUpdated(row, col);
  }

  public void updateTable(WorksheetModel w) {
    Map<Coord, Content> contentMap = w.getSheet();
    for (Coord c: contentMap.keySet()) {
      this.setValueAt(w.getValue(c), c.row - 1, c.col - 1);

    }
  }
}
 */
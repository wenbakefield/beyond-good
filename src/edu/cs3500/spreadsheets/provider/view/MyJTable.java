package edu.cs3500.spreadsheets.provider.view;

import javax.swing.JTable;
import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;

/**
 * implementation of a JTable class to hold an instance of the MyTableModel class.
 */
public class MyJTable extends JTable {
  WorksheetModel model;
  MyTableModel t;


  /**
   * basic constructor for instantiating the JTable.
   * @param t the TableModel class for JTable.
   */
  public MyJTable(MyTableModel t) {
    super(t);
    this.t = t;
  }

  /**
   * sets the worksheet that the JTable reads from.
   * @param model the worksheetmodel to be added.
   */
  public void setWorksheet(WorksheetModel model) {
    this.model = model;
  }

  /**
   * returns the worksheet the JTable reads from.
   * @return the worksheetmodel.
   */
  public WorksheetModel getWorksheet() {
    return model;
  }

  /**
   * returns the MyTableModel class as opposed to a basic TableModel.
   * @return the MyTableModel field.
   */
  public MyTableModel getMyModel() {
    return t;
  }



}

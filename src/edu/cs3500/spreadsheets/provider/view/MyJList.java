package edu.cs3500.spreadsheets.provider.view;

import javax.swing.JList;

/**
 * implementation of a JList to keep an instance of our RowHeader class.
 */
public class MyJList extends JList {
  private RowHeader rowheader;

  /**
   * Generic constructor taking in a RowHeader class as the ListModel.
   * @param lm the ListModel for the JList class.
   */
  public MyJList(RowHeader lm) {
    super(lm);
    rowheader = lm;
  }

  /**
   * To get the RowHeader class instead of a generic ListModel class.
   * @return the rowHeader isntance.
   */
  public RowHeader getMyModel() {
    return rowheader;
  }
}

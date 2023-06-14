package edu.cs3500.spreadsheets.provider.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.AbstractListModel;

/**
 * Implementation of a ListModel class specifically for creating the RowHeaders.
 */
public class RowHeader extends AbstractListModel implements ListModel {
  private List<String> header;


  /**
   * Generic constructor to create the list of row headers.
   * @param maxY how many rows there are in the table.
   */
  public RowHeader(int maxY) {
    header = new ArrayList<>();
    header.addAll(Arrays.asList(setHeader(maxY)));
  }

  @Override
  public int getSize() {
    return header.size();
  }

  @Override
  public String getElementAt(int index) {
    return header.get(index);
  }

  /**
   * adds a row the header if the user scrolls past the limit.
   */
  public void addRow() {
    header.add(Integer.toString(header.size() + 1));
  }

  /**
   * creates an array of headers to be displayed.
   * @param maxY the number of rows in the table.
   * @return an array containing the headers for each row.
   */
  public String[] setHeader(int maxY) {
    String[] header = new String[maxY];
    for (int i = 0; i < maxY; i++) {
      header[i] = Integer.toString(i + 1);
    }
    return header;
  }
}

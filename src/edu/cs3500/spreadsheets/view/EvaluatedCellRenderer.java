package edu.cs3500.spreadsheets.view;

import javax.swing.table.DefaultTableCellRenderer;

import edu.cs3500.spreadsheets.model.cell.CellContent;
import edu.cs3500.spreadsheets.model.value.ValueContent;

/**
 * Represented a custom cell renderer that sets the text of each cell in the table
 * to its evaluated content. Overrides the default JTable cell renderer.
 */
public class EvaluatedCellRenderer extends DefaultTableCellRenderer {
  EvaluatedCellRenderer() {
    super();
  }

  @Override
  public void setValue(Object value) {
    if (value == null) {
      setText("");
    } else {
      ValueContent content = ((CellContent) value).getValueContent();
      setText(content.toString().replaceAll("^\"|\"$", ""));
    }
  }
}
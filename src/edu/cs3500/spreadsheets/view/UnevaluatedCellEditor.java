package edu.cs3500.spreadsheets.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

import edu.cs3500.spreadsheets.model.cell.CellContent;

/**
 * Represented a custom cell editor that opens an input dialog to edit the raw contents of a cell
 * that has been double-clicked. Overrides the default JTable cell editor.
 */
public class UnevaluatedCellEditor extends AbstractCellEditor implements TableCellEditor,
        ActionListener {
  String newInput;
  String oldValue;
  JButton button;
  static final String EDIT = "edit";

  UnevaluatedCellEditor() {
    button = new JButton();
    button.setBackground(Color.WHITE);
    button.setBorderPainted(true);
    button.setActionCommand(EDIT);
    button.addActionListener(this);
    button.setHorizontalAlignment(JButton.LEFT);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (EDIT.equals(e.getActionCommand())) {
      UIManager.put("OptionPane.minimumSize", new Dimension(800, 100));
      if (oldValue.startsWith("\"")) {
        newInput = "\"" + JOptionPane.showInputDialog("Edit",
                oldValue.replaceAll("^\"|\"$", "")) + "\"";
      } else {
        newInput = JOptionPane.showInputDialog("Edit",
                oldValue.replaceAll("^\"|\"$", ""));
      }
      if (newInput == null) {
        newInput = oldValue;
      }
      fireEditingStopped();
    }
    UIManager.put("OptionPane.minimumSize", new Dimension(262, 90));
  }

  @Override
  public Object getCellEditorValue() {
    return newInput;
  }

  @Override
  public Component getTableCellEditorComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               int row,
                                               int column) {

    newInput = value.toString();
    oldValue = value.toString();
    button.setText(((CellContent) value).getValueContent().toString()
            .replaceAll("^\"|\"$", ""));
    return button;
  }

  @Override
  public boolean isCellEditable(EventObject anEvent) {
    if (anEvent instanceof MouseEvent) {
      return ((MouseEvent) anEvent).getClickCount() >= 2;
    }
    return true;
  }
}
package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;

import edu.cs3500.spreadsheets.controller.SpreadsheetEditorAdapter;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;

/**
 * Represents an implementation of JPanel that contains a JTable containing the data of the cells
 * in the given spreadsheet model. The SpreadsheetPanel is used to display the spreadsheet in both
 * the read-only view and the editor view.
 */
class SpreadsheetPanel extends JPanel {
  private SpreadsheetEditorAdapter adapterModel;
  private JTable table;
  private JPanel rowHeader;
  private JScrollPane scrollPane;

  /**
   * Constructs a SpreadsheetPanel using the given model and sets the editing permission
   * of the JTable to the given boolean.
   * @param model the model to be rendered
   * @param canEdit whether or not the model should be editable
   */
  SpreadsheetPanel(SpreadsheetModel model, boolean canEdit) {
    this.adapterModel = new SpreadsheetEditorAdapter(model, canEdit);
    this.table = new JTable(this.adapterModel);
    for (int i = 0; i < this.table.getColumnModel().getColumnCount(); i++) {
      TableColumn column = this.table.getColumnModel().getColumn(i);
      column.setPreferredWidth(100);
      column.setMinWidth(100);
    }
    this.table.setDefaultRenderer(Object.class, new EvaluatedCellRenderer());
    this.table.setDefaultEditor(Object.class, new UnevaluatedCellEditor());
    this.rowHeader = new JPanel(null);
    this.buildTable();
  }

  private void buildTable() {
    // Create ScrollPane
    scrollPane = new JScrollPane(table,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setFillsViewportHeight(true);

    // Build Row Header
    this.updateHeader();

    // Add ScrollPane to Panel
    this.setLayout(new BorderLayout());
    this.add(scrollPane, BorderLayout.CENTER);
    this.setPreferredSize(new Dimension(1600, 900));
  }

  private void updateHeader() {
    Component labelTemplate = table.getTableHeader().getDefaultRenderer()
            .getTableCellRendererComponent(table, table.getColumnModel().getColumn(0)
                    .getHeaderValue(), false, false, 0, 0);
    rowHeader = new JPanel(null);
    rowHeader.setPreferredSize(new Dimension(100,
            table.getRowHeight() * adapterModel.getRowCount()));
    for (int i = 0; i < adapterModel.getRowCount(); i++) {
      JLabel rowLabel = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
      rowLabel.setFont(labelTemplate.getFont());
      rowLabel.setBackground(labelTemplate.getBackground());
      rowLabel.setForeground(labelTemplate.getForeground());
      rowLabel.setBorder((Border) UIManager.getDefaults().get("TableHeader.cellBorder"));
      rowLabel.setBounds(0, i * table.getRowHeight(), 100, table.getRowHeight());
      rowHeader.add(rowLabel);
    }
    JViewport headerViewport = new JViewport();
    headerViewport.setViewSize(
            new Dimension(100, table.getRowHeight() * adapterModel.getRowCount()));
    headerViewport.setView(rowHeader);
    scrollPane.setRowHeader(headerViewport);
  }

  /**
   * Retrieves the original model from the adapter model used to communicate with the view.
   * Necessary to save the spreadsheet to a file.
   * @return the SpreadsheetModel
   */
  SpreadsheetModel getModel() {
    return this.adapterModel.getModel();
  }

  /**
   * Adds the specified number of new columns to the JTable within this JPanel.
   * @param numCols the number of new columns to add
   */
  void addColumns(int numCols) {
    for (int i = 0; i < numCols; i++) {
      this.table.addColumn(new TableColumn(this.table.getColumnCount(), 100,
              this.table.getDefaultRenderer(Object.class), this.table.getCellEditor()));
    }
  }

  /**
   * Adds the specified number of new rows to the JTable within this JPanel.
   * @param numRows the number of new rows to add
   */
  void addRows(int numRows) {
    for (int i = 0; i < numRows; i++) {
      this.adapterModel.addRow();
    }
    this.updateHeader();
  }
}
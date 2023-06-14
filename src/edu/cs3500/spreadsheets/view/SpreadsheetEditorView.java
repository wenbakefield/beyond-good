package edu.cs3500.spreadsheets.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.cs3500.spreadsheets.BeyondGood;
import edu.cs3500.spreadsheets.model.BasicSpreadsheetBuilder;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;

/**
 * An implementation of SpreadsheetView which renders a SpreadsheetModel of cells in a graphical
 * window in which the spreadsheet can be modified. New spreadsheets can also be created or loaded
 * using the menu.
 */
public class SpreadsheetEditorView extends JFrame implements SpreadsheetView {
  private SpreadsheetPanel panel;

  /**
   * Constructs a SpreadsheetEditorView with the given model being used to render the spreadsheet.
   *
   * @param model the editable model to be rendered
   */
  public SpreadsheetEditorView(SpreadsheetModel model) {
    JMenuBar menuBar = new JMenuBar();

    // Initialize File menu
    JMenu fileMenu = new JMenu("File");
    fileMenu.setToolTipText(
            "Save this file, load a file, or create a new file");
    menuBar.add(fileMenu);

    // Initialize Save item in File menu
    JMenuItem saveItem = new JMenuItem("Save");
    saveItem.setToolTipText(
            "Save the current spreadsheet to a file");
    saveItem.addActionListener(ae -> {
      final JFileChooser fileChooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "gOOD & txt files", "gOOD", "txt");
      fileChooser.setFileFilter(filter);
      int returnVal = fileChooser.showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        try {
          SpreadsheetModel spreadsheet = this.panel.getModel();
          PrintWriter printWriter = new PrintWriter(file + ".gOOD");
          SpreadsheetTextualView textualView = new SpreadsheetTextualView(spreadsheet, printWriter);
          textualView.render();
          printWriter.flush();
          printWriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    fileMenu.add(saveItem);

    // Initialize Load item in File menu
    JMenuItem loadItem = new JMenuItem("Load");
    loadItem.setToolTipText(
            "Load a spreadsheet from a file");
    loadItem.addActionListener(ae -> {
      final JFileChooser fileChooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "gOOD & txt files", "gOOD", "txt");
      fileChooser.setFileFilter(filter);
      int returnVal = fileChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        BufferedReader fileIn = null;
        try {
          fileIn = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        SpreadsheetModel spreadsheet = WorksheetReader.read(new BasicSpreadsheetBuilder(), fileIn);
        SpreadsheetEditorView editView = new SpreadsheetEditorView(spreadsheet);
        editView.render();
        editView.setVisible(true);
      }
    });
    fileMenu.add(loadItem);

    // Initialize New item in File menu
    JMenuItem newItem = new JMenuItem("New");
    newItem.setToolTipText(
            "Create a new blank spreadsheet");
    newItem.addActionListener(ae -> {
      Object[] options = {"Create new spreadsheet", "Cancel"};
      int n = JOptionPane.showOptionDialog(this,
              "Are you sure you would like to create a new spreadsheet?\n" +
                      "The existing spreadsheet will be erased if it is not saved!",
              "New", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
              options, options[1]);
      if (n == (JOptionPane.YES_OPTION)) {
        try {
          BeyondGood.main(new String[]{"-edit"});
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    fileMenu.add(newItem);

    // Initialize Edit menu
    JMenu editMenu = new JMenu("Edit");
    editMenu.setToolTipText(
            "Add additional rows or columns");
    menuBar.add(editMenu);

    // Initialize Add Row item in Edit menu
    JMenuItem addRowItem = new JMenuItem("Add row(s)");
    addRowItem.setToolTipText(
            "Adds additional rows to the spreadsheet");
    addRowItem.addActionListener(ae ->
            this.panel.addRows(Integer.parseInt(
                    JOptionPane.showInputDialog("Enter number of additional rows:"))));
    editMenu.add(addRowItem);

    // Initialize Add Column item in Edit menu
    JMenuItem addColumnItem = new JMenuItem("Add column(s)");
    addColumnItem.setToolTipText(
            "Adds additional columns to the spreadsheet");
    addColumnItem.addActionListener(ae ->
            this.panel.addColumns(Integer.parseInt(
                    JOptionPane.showInputDialog("Enter number of additional columns:"))));

    editMenu.add(addColumnItem);

    // Initialize the menu bar
    this.setJMenuBar(menuBar);

    panel = new SpreadsheetPanel(model, true);
    panel.setOpaque(true);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(panel);
    this.setTitle("Spreadsheet Editor");
  }

  @Override
  public void render() {
    this.pack();
    this.setVisible(true);
  }
}
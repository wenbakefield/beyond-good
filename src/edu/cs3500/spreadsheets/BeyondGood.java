package edu.cs3500.spreadsheets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.model.BasicSpreadsheet;
import edu.cs3500.spreadsheets.model.BasicSpreadsheetBuilder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.provider.adapter.WorksheetModel;
import edu.cs3500.spreadsheets.provider.view.Controller;
import edu.cs3500.spreadsheets.provider.view.GraphicView;
import edu.cs3500.spreadsheets.view.SpreadsheetEditorView;
import edu.cs3500.spreadsheets.view.SpreadsheetReadOnlyView;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;

/**
 * Contains the main method for executing the spreadsheet application.
 */
public class BeyondGood {

  /**
   * The main method for the spreadsheet application. Takes in a command line with a file name for
   * a spreadsheet and either:
   * -eval some-cellname : Evaluates the specified cell.
   * -save -some-filename : Saves the spreadsheet to a new file.
   * -gui : Opens the visual view of the file.
   * -gui with no specified file creates a new blank spreadsheet.
   * @param args the command line e.g. "-in some-filename -eval some-cellname"
   */
  public static void main(String[] args) throws IOException {
    BasicSpreadsheet spreadsheet;
    if (args.length > 1) {
      BufferedReader fileIn = new BufferedReader(new FileReader(args[1]));
      spreadsheet = WorksheetReader.read(new BasicSpreadsheetBuilder(), fileIn);
      if (args.length == 4 && args[2].equals("-eval")) {
        String cell = args[3];
        char[] cellChars = cell.toCharArray();
        int rowIndex = 0;
        for (int i = 0; i < cellChars.length; i++) {
          if (Character.isDigit(cellChars[i])) {
            rowIndex = i;
            break;
          }
        }
        String column = cell.substring(0, rowIndex);
        String row = cell.substring(rowIndex);
        try {
          Coord coord = new Coord(Coord.colNameToIndex(column),
                  Integer.parseInt(row));
          try {
            System.out.println(spreadsheet.getEvaluatedCell(coord));
          } catch (Exception e) {
            System.out.println("Error in cell " + column + row + ": " + e.getMessage());
          }
        } catch (NumberFormatException e) {
          System.out.println("The command-line is malformed!");
        }
      } else if (args.length == 4 && args[2].equals("-save")) {
        PrintWriter writer = new PrintWriter(args[3]);
        SpreadsheetTextualView textualView = new SpreadsheetTextualView(spreadsheet, writer);
        textualView.render();
        writer.flush();
        writer.close();
      } else if (args.length == 3 && args[2].equals("-gui")) {
        SpreadsheetReadOnlyView readOnly = new SpreadsheetReadOnlyView(spreadsheet);
        readOnly.render();
        readOnly.setVisible(true);
      } else if (args.length == 3 && args[2].equals("-edit")) {
        SpreadsheetEditorView editView = new SpreadsheetEditorView(spreadsheet);
        editView.render();
        editView.setVisible(true);
      } else {
        System.out.println("The command-line is malformed!");
      }
    } else {
      if (args.length == 1 && args[0].equals("-gui")) {
        SpreadsheetReadOnlyView readOnlyView = new SpreadsheetReadOnlyView(new BasicSpreadsheet());
        readOnlyView.render();
        readOnlyView.setVisible(true);
      } else if (args.length == 1 && args[0].equals("-edit")) {
        SpreadsheetEditorView editView = new SpreadsheetEditorView(new BasicSpreadsheet());
        editView.render();
        editView.setVisible(true);
      } else if (args.length == 1 && args[0].equals("-provider")) {
        GraphicView editView = new GraphicView(new WorksheetModel(new BasicSpreadsheet()));
        editView.setVisible(true);
        new Controller(editView);
      } else {
        System.out.println("The command-line is malformed!");
      }
    }
  }

  /**
   * Returns a spreadsheet with the given input data loaded into the model.
   * @param in the file or data to be loaded
   * @return the populated spreadsheet
   */
  public static SpreadsheetModel loadSpreadsheet(Readable in) {
    return new WorksheetReader().read(new BasicSpreadsheetBuilder(), in);
  }
}
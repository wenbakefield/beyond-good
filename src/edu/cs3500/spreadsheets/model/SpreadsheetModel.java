package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellContent;
import edu.cs3500.spreadsheets.model.value.ValueContent;

/**
 * The model for an interactive spreadsheet program: this maintains
 * the state of the spreadsheet and handles modifications.
 * @param <K>  the type of cells in the spreadsheet
 */
public interface SpreadsheetModel<K> {

  /**
   * Updates the contents of the cell at the given coord with the given contents.
   * @param coord the location of the cell in the grid
   * @param contents the new contents of the cell
   */
  void modifyCell(Coord coord, String contents);

  /**
   * Returns the evaluated contents of the cell at the given coord.
   * @param coord the location of the cell in the grid
   * @return the evaluated contents of the cell
   */
  String getEvaluatedCell(Coord coord);

  /**
   * Returns the raw ValueContent of the cell at the given coord.
   * @param coord the location of the cell in the grid.
   * @return the ValueContent of the cell at the given coord.
   */
  ValueContent getValueContent(Coord coord);

  /**
   * Returns the raw CellContent of the cell at the given coord.
   * @param coord the location of the cell in the grid.
   * @return the CellContent of the cell at the given coord.
   */
  CellContent getCellContent(Coord coord);

  /**
   * Returns the number of rows in the spreadsheet.
   * @return the number of rows
   */
  int getNumRows();

  /**
   * Returns the number of columns in the spreadsheet.
   * @return the number of columns
   */
  int getNumCols();

  /**
   * Returns a list of cells currently in the grid.
   * @return the list of cells
   */
  List<K> getCells();

  /**
   * Returns a list of coords currently occupied in the grid.
   * @return the list of coords
   */
  List<Coord> getCoords();

  /**
   * Returns the grid of Coords mapped to their respective Cells.
   * @return the HashMap of Coords and Cells
   */
  HashMap<Coord, Cell> getSheet();

}
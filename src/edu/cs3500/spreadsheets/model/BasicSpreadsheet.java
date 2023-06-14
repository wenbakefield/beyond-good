package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellContent;
import edu.cs3500.spreadsheets.model.value.EmptyValue;
import edu.cs3500.spreadsheets.model.value.ValueContent;
import edu.cs3500.spreadsheets.model.visitor.ConcatVisitor;
import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;
import edu.cs3500.spreadsheets.model.visitor.LessThanVisitor;
import edu.cs3500.spreadsheets.model.visitor.ProdVisitor;
import edu.cs3500.spreadsheets.model.visitor.SumVisitor;
import edu.cs3500.spreadsheets.sexp.ConvertToContentVisitor;
import edu.cs3500.spreadsheets.sexp.Parser;

/**
 * Represents an implementation of WorksheetModel. Maintains the state of the
 * spreadsheet as the user interacts with it.
 * @param <K> the types of cells that the spreadsheet uses
 */
public class BasicSpreadsheet<K> implements SpreadsheetModel<Cell> {
  private HashMap<Coord, Cell> grid;
  private HashMap<String, ContentVisitor> functions;

  /**
   * Constructs a BasicSpreadsheet with the given arguments.
   * @param grid      the HashMap of all cells mapped to their coordinates
   * @param functions the HashMap of all function CellVisitors mapped to their names
   */
  BasicSpreadsheet(HashMap<Coord, Cell> grid, HashMap<String, ContentVisitor> functions) {
    if (grid == null) {
      throw new IllegalArgumentException("Grid cannot be null!");
    }
    this.grid = grid;
    this.functions = functions;
  }

  /**
   * Default constructor for a BasicSpreadsheet.
   */
  public BasicSpreadsheet() {
    this.grid = new HashMap<>();
    this.grid.put(new Coord(100, 100), new Cell(new Coord(100, 100),
            new EmptyValue()));
    HashMap<String, ContentVisitor> functions = new HashMap<>();
    functions.put("SUM", new SumVisitor());
    functions.put("PRODUCT", new ProdVisitor());
    functions.put("<", new LessThanVisitor());
    functions.put("CONCAT", new ConcatVisitor());
    this.functions = functions;
  }

  @Override
  public List<Cell> getCells() {
    return new ArrayList<>(grid.values());
  }

  @Override
  public List<Coord> getCoords() {
    return new ArrayList<>(grid.keySet());
  }

  @Override
  public HashMap<Coord, Cell> getSheet() {
    return this.grid;
  }

  @Override
  public ValueContent getValueContent(Coord coord) {
    if (grid.get(coord) == null) {
      throw new IllegalArgumentException("The requested cell cannot be null!");
    }
    return grid.get(coord).getValueContent();
  }

  @Override
  public CellContent getCellContent(Coord coord) {
    if (grid.get(coord) == null) {
      return new EmptyValue();
    }
    return grid.get(coord).getContent();
  }

  @Override
  public String getEvaluatedCell(Coord coord) {
    if (grid.get(coord) == null) {
      throw new IllegalArgumentException("The requested cell cannot be null!");
    }
    return grid.get(coord).getValueContent().toString();
  }

  @Override
  public void modifyCell(Coord coord, String contents) {
    ConvertToContentVisitor converter =
            new ConvertToContentVisitor(this.grid, this.functions);
    if (contents == null || contents.length() == 0) {
      grid.put(coord, new Cell(coord, new EmptyValue()));
    } else if (contents.substring(0, 1).equals("=")) {
      grid.put(coord, new Cell(coord, Parser.parse(contents.substring(1)).accept(converter)));
    } else {
      grid.put(coord, new Cell(coord, Parser.parse(contents).accept(converter)));
    }
  }

  @Override
  public int getNumRows() {
    if (grid == null || grid.isEmpty()) {
      return 0;
    } else {
      ArrayList<Integer> rows = new ArrayList<>();
      for (Coord coord : this.grid.keySet()) {
        rows.add(coord.row);
      }
      int maxRow = 0;
      for (Integer row : rows) {
        if (row > maxRow) {
          maxRow = row;
        }
      }
      return maxRow;
    }
  }

  @Override
  public int getNumCols() {
    if (grid == null || grid.isEmpty()) {
      return 0;
    } else {
      ArrayList<Integer> columns = new ArrayList<>();
      for (Coord coord : this.grid.keySet()) {
        columns.add(coord.col);
      }
      int maxColumn = 0;
      for (Integer column : columns) {
        if (column > maxColumn) {
          maxColumn = column;
        }
      }
      return maxColumn;
    }
  }

}
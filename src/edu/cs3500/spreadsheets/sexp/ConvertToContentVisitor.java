package edu.cs3500.spreadsheets.sexp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellContent;
import edu.cs3500.spreadsheets.model.cell.FunctionContent;
import edu.cs3500.spreadsheets.model.cell.MultiReference;
import edu.cs3500.spreadsheets.model.cell.SingleReference;
import edu.cs3500.spreadsheets.model.value.EmptyValue;
import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;
import edu.cs3500.spreadsheets.model.value.BooleanValue;
import edu.cs3500.spreadsheets.model.value.DoubleValue;
import edu.cs3500.spreadsheets.model.value.StringValue;

/**
 * Represents an implementation of SexpVisitor that processes Sexpressions and
 * converts them into equivalent CellContents.
 */
public class ConvertToContentVisitor implements SexpVisitor<CellContent> {
  private HashMap<Coord, Cell> grid;
  private HashMap<String, ContentVisitor> functions;

  /**
   * The constructor for a ConvertToContentVisitor taking in a HashMap mapping Coords to Cells
   * and a HashMap mapping String function names to ContentVisitors.
   * @param grid      a map between Coords and their corresponding Cells
   * @param functions a map between String function names and their corresponding ContentVisitors
   */
  public ConvertToContentVisitor(HashMap<Coord, Cell> grid, HashMap<String,
          ContentVisitor> functions) {
    if (grid == null) {
      throw new IllegalArgumentException("Grid must not be null!");
    }
    if (functions == null) {
      throw new IllegalArgumentException("Functions must not be null!");
    }
    this.grid = grid;
    this.functions = functions;
  }

  @Override
  public CellContent visitBoolean(boolean b) {
    return new BooleanValue(b);
  }

  @Override
  public CellContent visitNumber(double d) {
    return new DoubleValue(d);
  }

  @Override
  public CellContent visitSList(List<Sexp> l) {
    List<CellContent> cellContents = new ArrayList<>();
    for (int i = 1; i < l.size(); i++) {
      cellContents.add(l.get(i).accept(this));
    }
    String command = (String) l.get(0).accept(this).getValueContent().getValue();
    if (!functions.containsKey(command)) {
      throw new IllegalArgumentException("Invalid function! Command: " + command);
    }
    ContentVisitor function = functions.get(command);
    return new FunctionContent(function, cellContents);
  }

  @Override
  public CellContent visitSymbol(String s) {
    if (functions.containsKey(s)) {
      return this.visitString(s);

    } else if (s.contains(":") && (s.length() - 1 != s.indexOf(":"))) {
      Coord firstCoord = convertToCoord(s.substring(0, s.indexOf(":")));
      Coord secondCoord = convertToCoord(s.substring(s.indexOf(":") + 1));
      Coord leftBound;
      Coord rightBound;
      if (firstCoord.col > secondCoord.col || firstCoord.row > secondCoord.row) {
        leftBound = secondCoord;
        rightBound = firstCoord;
      } else {
        leftBound = firstCoord;
        rightBound = secondCoord;
      }
      List<CellContent> cellContents = new ArrayList<>();
      for (int i = leftBound.row; i <= rightBound.row; i++) {
        for (int j = leftBound.col; j <= rightBound.col; j++) {
          Coord currentCoord = new Coord(j, i);
          if (grid.containsKey(currentCoord)) {
            cellContents.add(grid.get(currentCoord).getContent());
          } else {
            grid.put(currentCoord, new Cell(currentCoord, new EmptyValue()));
            cellContents.add(grid.get(currentCoord).getContent());
          }
        }
      }
      return new MultiReference(cellContents, firstCoord, secondCoord);

    } else if (!s.contains(":")) {
      Coord referenceCoord = convertToCoord(s);
      List<CellContent> cellContents = new ArrayList<>();
      if (grid.containsKey(referenceCoord)) {
        cellContents.add(grid.get(referenceCoord).getContent());
      } else {
        grid.put(referenceCoord, new Cell(referenceCoord, new EmptyValue()));
        cellContents.add(grid.get(referenceCoord).getContent());
      }
      return new SingleReference(cellContents, referenceCoord);

    } else {
      throw new IllegalArgumentException("Invalid symbol! Symbol: " + s);
    }
  }

  /**
   * Converts the given string to a Coord.
   * @param name the name of the Cell
   * @return the corresponding Coord of the Cell
   */
  private Coord convertToCoord(String name) {
    char[] nameChars = name.toCharArray();
    int rowIndex = 0;
    for (int i = 0; i < nameChars.length; i++) {
      if (Character.isDigit(nameChars[i])) {
        rowIndex = i;
        break;
      }
    }
    if (rowIndex <= 0) {
      throw new IllegalArgumentException("Column could not be found!");
    }
    for (int i = rowIndex; i < nameChars.length; i++) {
      if (!Character.isDigit(nameChars[i])) {
        throw new IllegalArgumentException("Invalid symbol! Symbol: " + name);
      }
    }
    return new Coord(Coord.colNameToIndex(name.substring(0, rowIndex)),
            Integer.parseInt(name.substring(rowIndex)));
  }

  @Override
  public CellContent visitString(String s) {
    return new StringValue(s);
  }
}
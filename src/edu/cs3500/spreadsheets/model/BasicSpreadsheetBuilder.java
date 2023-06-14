package edu.cs3500.spreadsheets.model;

import java.util.HashMap;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.value.EmptyValue;
import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;
import edu.cs3500.spreadsheets.model.visitor.ConcatVisitor;
import edu.cs3500.spreadsheets.model.visitor.LessThanVisitor;
import edu.cs3500.spreadsheets.model.visitor.ProdVisitor;
import edu.cs3500.spreadsheets.model.visitor.SumVisitor;
import edu.cs3500.spreadsheets.sexp.ConvertToContentVisitor;
import edu.cs3500.spreadsheets.sexp.Parser;

/**
 * Represents an implementation of WorksheetBuilder that builds a BasicSpreadsheet.
 */
public class BasicSpreadsheetBuilder implements WorksheetBuilder<BasicSpreadsheet> {
  private HashMap<Coord, Cell> grid = new HashMap<>();
  private HashMap<String, ContentVisitor> functions = this.loadDefaultFunctions();

  private HashMap<String, ContentVisitor> loadDefaultFunctions() {
    HashMap<String, ContentVisitor> functions = new HashMap<>();
    functions.put("SUM", new SumVisitor());
    functions.put("PRODUCT", new ProdVisitor());
    functions.put("<", new LessThanVisitor());
    functions.put("CONCAT", new ConcatVisitor());
    return functions;
  }

  @Override
  public WorksheetBuilder<BasicSpreadsheet> createCell(int col, int row, String contents) {
    Coord coord = new Coord(col, row);
    ConvertToContentVisitor converter = new ConvertToContentVisitor(grid, functions);
    if (contents == null) {
      return this;
    } else if (contents.length() == 0) {
      grid.put(coord, new Cell(coord, new EmptyValue()));
    } else if (contents.substring(0, 1).equals("=")) {
      grid.put(coord, new Cell(coord, Parser.parse(contents.substring(1)).accept(converter)));
    } else {
      grid.put(coord, new Cell(coord, Parser.parse(contents).accept(converter)));
    }
    return this;
  }

  @Override
  public BasicSpreadsheet createWorksheet() {
    return new BasicSpreadsheet(this.grid, this.functions);
  }

}
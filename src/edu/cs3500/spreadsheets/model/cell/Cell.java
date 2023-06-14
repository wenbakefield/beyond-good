package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.value.ValueContent;

/**
 * Represents a cell in a spreadsheet. A cell contains its coord, its content, and a list of cells
 * that reference it.
 */
public class Cell {
  public final Coord coord;
  private CellContent content;
  private List<Cell> referencers;

  /**
   * Constructs a Cell with the given arguments.
   *
   * @param coord   the Coord the Cell is located at
   * @param content the CellContent that the Cell holds
   */
  public Cell(Coord coord, CellContent content) {
    if (coord == null || content == null) {
      throw new IllegalArgumentException("Content cannot be null!");
    }
    this.coord = coord;
    this.content = content;
    this.referencers = new ArrayList<>();
  }

  public ValueContent getValueContent() {
    return this.content.getValueContent();
  }

  public CellContent getContent() {
    return this.content;
  }

  @Override
  public String toString() {
    if (this.content instanceof FunctionContent) {
      return coord.toString() + " =" + content.toString();
    } else if (this.content instanceof ReferenceContent) {
      return coord.toString();
    } else {
      return coord.toString() + " " + content.toString();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell that = (Cell) o;
    return Objects.equals(this.coord, that.coord) &&
            Objects.equals(this.content, that.content) &&
            Objects.equals(this.referencers, that.referencers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.coord, this.content, this.referencers);
  }
}
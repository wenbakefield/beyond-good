package edu.cs3500.spreadsheets.model.cell;

import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a ReferenceContent of a single cell, with the location of the cell stored as a coord.
 */
public class SingleReference extends ReferenceContent {
  private Coord coord;

  /**
   * Constructs a SingleReference containing the given list of CellContent at the given coord.
   *
   * @param contents the list of CellContent being referenced
   * @param coord    the location of the reference
   */
  public SingleReference(List<CellContent> contents, Coord coord) {
    super(contents);
    this.coord = coord;
  }

  @Override
  public String toString() {
    return coord.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SingleReference that = (SingleReference) o;
    return Objects.equals(this.contents, that.contents)
            && Objects.equals(this.coord, that.coord);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.contents, this.coord);
  }

}
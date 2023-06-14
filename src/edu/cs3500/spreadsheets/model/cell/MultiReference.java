package edu.cs3500.spreadsheets.model.cell;

import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a ReferenceContent of a bounded region, with each bound representing the extents of
 * the region.
 */
public class MultiReference extends ReferenceContent {
  private Coord leftBound;
  private Coord rightBound;

  /**
   * Constructs a MultiReference using the given arguments.
   *
   * @param leftBound  the left bound of the referenced region
   * @param rightBound the right bound of the referenced region
   */
  public MultiReference(List<CellContent> contents, Coord leftBound, Coord rightBound) {
    super(contents);
    if (contents == null || leftBound == null || rightBound == null) {
      throw new IllegalArgumentException("Arguments must not be null!");
    }
    this.leftBound = leftBound;
    this.rightBound = rightBound;
  }

  @Override
  public String toString() {
    return leftBound.toString() + ":" + rightBound.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultiReference that = (MultiReference) o;
    return Objects.equals(this.contents, that.contents)
            && Objects.equals(this.leftBound, that.leftBound)
            && Objects.equals(this.rightBound, that.rightBound);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.contents, this.leftBound, this.rightBound);
  }

}

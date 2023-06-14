package edu.cs3500.spreadsheets.model.cell;

import java.util.List;

import edu.cs3500.spreadsheets.model.value.ValueContent;
import edu.cs3500.spreadsheets.model.visitor.ContentVisitor;

/**
 * Represents an abstraction of a ReferenceContent, which can either be a SingleReference or a
 * MultiReference.
 */
public abstract class ReferenceContent implements CellContent {
  List<CellContent> contents;

  /**
   * Constructor for passing the ReferenceContent contents to subclasses.
   *
   * @param contents the list of CellContent contained in the ReferenceContent
   */
  public ReferenceContent(List<CellContent> contents) {
    this.contents = contents;
  }

  @Override
  public <R> R accept(ContentVisitor<R> visitor) {
    return visitor.visitReferenceContent(this);
  }

  @Override
  public ValueContent getValueContent() {
    return this.contents.get(0).getValueContent();
  }

  /**
   * Returns the list of CellContent contained in this ReferenceContent.
   *
   * @return the list of CellContent
   */
  public List<CellContent> getContents() {
    return this.contents;
  }

}
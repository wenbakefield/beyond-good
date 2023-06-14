import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.cs3500.spreadsheets.BeyondGood;
import edu.cs3500.spreadsheets.controller.SpreadsheetEditorAdapter;
import edu.cs3500.spreadsheets.model.BasicSpreadsheetBuilder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.value.BooleanValue;
import edu.cs3500.spreadsheets.model.value.ValueContent;
import edu.cs3500.spreadsheets.model.value.DoubleValue;
import edu.cs3500.spreadsheets.model.value.StringValue;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;

/**
 * Tests for all BeyondGood spreadsheet model methods. Functionality is tested in the main method
 * using an input text file and program arguments.
 */
public class BeyondGoodTest {
  private ValueContent boolA;
  private ValueContent boolB;
  private ValueContent stringA;
  private ValueContent stringB;
  private ValueContent numberA;
  private ValueContent numberB;
  private ValueContent numberC;
  private ValueContent numberD;
  private SpreadsheetModel spreadsheet;
  private SpreadsheetModel spreadsheetCycles;
  private SpreadsheetModel emptySheet;

  @Before
  public void init() {
    boolA = new BooleanValue(true);
    boolB = new BooleanValue(false);
    stringA = new StringValue("hello world");
    stringB = new StringValue("soylent green");
    ValueContent stringC = new StringValue("Jack says \"hi\". Jill has one backslash \\ here.");
    numberA = new DoubleValue(42.0);
    numberB = new DoubleValue(-7.0);
    numberC = new DoubleValue(0.0);
    numberD = new DoubleValue(7.0);
    spreadsheet = new BasicSpreadsheetBuilder()
            .createCell(1, 1, "5")
            .createCell(1, 2, "0")
            .createCell(1, 3, "42")
            .createCell(1, 4, "68")
            .createCell(1, 5, "108")
            .createCell(2, 1, "\"hi\"")
            .createCell(2, 2, "\"soylent green\"")
            .createCell(2, 3, "\"!@#$%^&*()_+}{:?><|,./;'[]=-`~\"")
            .createCell(2, 4, "\"123456789\"")
            .createCell(2, 5, "\"Supercalifragilisticexpealidocious!\"")
            .createCell(3, 1, "true")
            .createCell(3, 2, "false")
            .createCell(3, 3, "-5")
            .createCell(3, 4, "1000000000000000000000000000000000")
            .createCell(3, 5, "=(CONCAT A1 A4 B2 C1 D1)")
            .createCell(4, 1, "=(SUM A3 A3 A3)")
            .createCell(4, 2, "=(PRODUCT (SUM C2:C3) (SUM A1 A3))")
            .createCell(4, 3, "=(< A1 D1)")
            .createCell(4, 4, "=(< D1 A1)")
            .createCell(4, 5, "=(CONCAT A1:B5)")
            .createCell(482, 1, "72")
            .createCell(1, 103, "103")
            .createCell(23847392, 4392738, "11112222344456666")
            .createCell(999999999, 999999999, "=(SUM B3 A1 B1:D1)")
            .createCell(5, 5, "")
            .createWorksheet();
    emptySheet = new BasicSpreadsheetBuilder().createWorksheet();
    spreadsheetCycles = new BasicSpreadsheetBuilder().createWorksheet();
  }

  @Test
  public void testEmptySpreadsheet() {
    assertEquals(0, emptySheet.getNumCols());
    assertEquals(0, emptySheet.getNumRows());
    assertEquals(999999999, spreadsheet.getNumCols());
    assertEquals(999999999, spreadsheet.getNumRows());
    emptySheet.modifyCell(new Coord(1, 1), "\"Hello!\"");
    assertEquals(1, emptySheet.getNumCols());
    assertEquals(1, emptySheet.getNumRows());
    emptySheet.modifyCell(new Coord(1, 2), "\"Wow!\"");
    assertEquals(1, emptySheet.getNumCols());
    assertEquals(2, emptySheet.getNumRows());
  }

  @Test
  public void verifySpreadsheetCells() {
    assertEquals("5.0",
            spreadsheet.getEvaluatedCell(new Coord(1, 1)));
    assertEquals("0.0",
            spreadsheet.getEvaluatedCell(new Coord(1, 2)));
    assertEquals("42.0",
            spreadsheet.getEvaluatedCell(new Coord(1, 3)));
    assertEquals("68.0",
            spreadsheet.getEvaluatedCell(new Coord(1, 4)));
    assertEquals("108.0",
            spreadsheet.getEvaluatedCell(new Coord(1, 5)));
    assertEquals("\"hi\"",
            spreadsheet.getEvaluatedCell(new Coord(2, 1)));
    assertEquals("\"soylent green\"",
            spreadsheet.getEvaluatedCell(new Coord(2, 2)));
    assertEquals("\"!@#$%^&*()_+}{:?><|,./;'[]=-`~\"",
            spreadsheet.getEvaluatedCell(new Coord(2, 3)));
    assertEquals("\"123456789\"",
            spreadsheet.getEvaluatedCell(new Coord(2, 4)));
    assertEquals("\"Supercalifragilisticexpealidocious!\"",
            spreadsheet.getEvaluatedCell(new Coord(2, 5)));
    assertEquals("true",
            spreadsheet.getEvaluatedCell(new Coord(3, 1)));
    assertEquals("false",
            spreadsheet.getEvaluatedCell(new Coord(3, 2)));
    assertEquals("-5.0",
            spreadsheet.getEvaluatedCell(new Coord(3, 3)));
    assertEquals("1.0E33",
            spreadsheet.getEvaluatedCell(new Coord(3, 4)));
    assertEquals("\"5.0, 68.0, soylent green, true, \"",
            spreadsheet.getEvaluatedCell(new Coord(3, 5)));
    assertEquals("126.0",
            spreadsheet.getEvaluatedCell(new Coord(4, 1)));
    assertEquals("-235.0",
            spreadsheet.getEvaluatedCell(new Coord(4, 2)));
    assertEquals("true",
            spreadsheet.getEvaluatedCell(new Coord(4, 3)));
    assertEquals("false",
            spreadsheet.getEvaluatedCell(new Coord(4, 4)));
    assertEquals("\"5.0, hi, 0.0, soylent green, 42.0, " +
                    "!@#$%^&*()_+}{:?><|,./;'[]=-`~, 68.0, 123456789, 108.0, " +
                    "Supercalifragilisticexpealidocious!\"",
            spreadsheet.getEvaluatedCell(new Coord(4, 5)));
    assertEquals("72.0",
            spreadsheet.getEvaluatedCell(new Coord(482, 1)));
    assertEquals("103.0",
            spreadsheet.getEvaluatedCell(new Coord(1, 103)));
    assertEquals("1.1112222344456666E16",
            spreadsheet.getEvaluatedCell(new Coord(23847392, 4392738)));
    assertEquals("131.0",
            spreadsheet.getEvaluatedCell(new Coord(999999999, 999999999)));
    try {
      spreadsheet.getEvaluatedCell(new Coord(5, 5));
    } catch (IllegalStateException e) {
      assertEquals("Blank cell does not contain a value!", e.getMessage());
    }

    // Controller Model Tests
    SpreadsheetEditorAdapter adapterModel =
            new SpreadsheetEditorAdapter(spreadsheet, true);

    assertEquals("5.0", adapterModel.getValueAt(0, 0).toString());
    assertEquals("0.0", adapterModel.getValueAt(1, 0).toString());
    assertEquals("42.0", adapterModel.getValueAt(2, 0).toString());
    assertEquals("68.0", adapterModel.getValueAt(3, 0).toString());
    assertEquals("\"hi\"",
            adapterModel.getValueAt(0, 1).toString());
    assertEquals("\"soylent green\"",
            adapterModel.getValueAt(1, 1).toString());
    assertEquals("\"123456789\"",
            adapterModel.getValueAt(3, 1).toString());

    adapterModel.setValueAt(new StringValue("yo"),
            3, 1);
    assertEquals("\"yo\"",
            adapterModel.getValueAt(3, 1).toString());
    assertTrue(adapterModel.isCellEditable(3, 2));
    assertEquals("C", adapterModel.getColumnName(2));
  }

  @Test
  public void testValueToString() {
    assertEquals("true", boolA.toString());
    assertEquals("false", boolB.toString());
    assertEquals("\"hello world\"", stringA.toString());
    assertEquals("\"soylent green\"", stringB.toString());
    assertEquals("42.0", numberA.toString());
    assertEquals("-7.0", numberB.toString());
    assertEquals("0.0", numberC.toString());
    assertEquals("7.0", numberD.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDirectReference() {
    spreadsheetCycles.modifyCell(new Coord(1, 1), "=(SUM A1 A1)");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIndirectReference() {
    spreadsheetCycles.modifyCell(new Coord(1, 1), "=(SUM B2)");
    spreadsheetCycles.modifyCell(new Coord(2, 1), "=(SUM A1)");
  }

  @Test
  public void testTextualView() throws IOException {
    StringWriter testWriter1 = new StringWriter();
    SpreadsheetTextualView testEmptyView = new SpreadsheetTextualView(emptySheet, testWriter1);
    testEmptyView.render();
    assertEquals(testWriter1.toString(), "");

    StringWriter testWriter2 = new StringWriter();
    SpreadsheetModel spreadsheetTextTest = new BasicSpreadsheetBuilder()
            .createCell(1, 1, "1")
            .createCell(2, 2, "\"test\"")
            .createCell(3, 3, "true").createWorksheet();
    SpreadsheetTextualView testNonEmptyView =
            new SpreadsheetTextualView(spreadsheetTextTest, testWriter2);
    testNonEmptyView.render();
    assertEquals(testWriter2.toString(), "A1 1.0\nB2 \"test\"\nC3 true\n");
  }

  @Test
  public void testEqualSpreadsheets() throws IOException {
    Readable read1 = new StringReader("A1 1.0\nB2 \"test\"\nC3 true\n");
    SpreadsheetModel sheet1 = BeyondGood.loadSpreadsheet(read1);
    Appendable out1 = new StringWriter();
    SpreadsheetTextualView textView1 = new SpreadsheetTextualView(sheet1, out1);
    textView1.render();

    Readable read2 = new StringReader(out1.toString());
    SpreadsheetModel sheet2 = BeyondGood.loadSpreadsheet(read2);
    Appendable out2 = new StringWriter();
    SpreadsheetTextualView textView2 = new SpreadsheetTextualView(sheet2, out2);
    textView2.render();

    assertEquals(out1.toString(), out2.toString());
  }
}
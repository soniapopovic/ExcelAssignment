import static junit.framework.TestCase.assertEquals;

import edu.cs3500.spreadsheets.model.BooleanValue;
import edu.cs3500.spreadsheets.model.DoubleValue;
import edu.cs3500.spreadsheets.model.SSWorksheetBuilder;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import edu.cs3500.spreadsheets.model.StringValue;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;

/**
 * Tests functionality of {@link SSWorksheetBuilder}.
 */
public class BuilderTests {

  @Test
  public void testBuilderAllAddedCellsToSSAreThere() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "1");
    worksheetBuilder.createCell(1, 2, "=(SUM A1 1)");
    worksheetBuilder.createCell(1, 3, "true");
    worksheetBuilder.createCell(1, 4, "\"hello\"");
    worksheetBuilder.createCell(1, 5, "=(SUM A1 A1)");
    Spreadsheet ss = worksheetBuilder.createWorksheet();
    assertEquals(ss.getCellAt(1,1).getValue(), new DoubleValue(1.0));
    assertEquals(ss.getCellAt(1, 2).getValue(), new DoubleValue(2.0));
    assertEquals(ss.getCellAt(1, 3).getValue(), new BooleanValue(true));
    assertEquals(ss.getCellAt(1, 4).getValue(), new StringValue("hello"));
    assertEquals(ss.getCellAt(1, 5).getValue(), new DoubleValue(2.0));
  }

  @Test
  public void testSumExtensively() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "1");
    worksheetBuilder.createCell(1, 2, "=(SUM A1 1)");
    worksheetBuilder.createCell(1, 3, "true");
    worksheetBuilder.createCell(1, 4, "\"hello\"");
    worksheetBuilder.createCell(1, 5, "=(SUM A1 A1)");
    worksheetBuilder.createCell(1, 6, "=(SUM A1 A3)");
    worksheetBuilder.createCell(1, 7, "=(SUM A4 A3)");
    worksheetBuilder.createCell(1, 8, "=(SUM A4 A2)");
    worksheetBuilder.createCell(1, 9, "=(SUM A5 A5)");
    Spreadsheet ss = worksheetBuilder.createWorksheet();
    assertEquals(new DoubleValue(2.0), ss.getCellAt(1, 2).getValue());
    assertEquals(new DoubleValue(2.0), ss.getCellAt(1, 5).getValue());
    assertEquals(new DoubleValue(1.0), ss.getCellAt(1, 6).getValue());
    assertEquals(new DoubleValue(0.0), ss.getCellAt(1, 7).getValue());
    assertEquals(new DoubleValue(2.0), ss.getCellAt(1, 8).getValue());
    assertEquals(new DoubleValue(4.0), ss.getCellAt(1, 9).getValue());
  }

  @Test
  public void testProductExtensively() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "1");
    worksheetBuilder.createCell(1, 2, "=(SUM A1 1)");
    worksheetBuilder.createCell(1, 3, "true");
    worksheetBuilder.createCell(1, 4, "\"hello\"");
    worksheetBuilder.createCell(1, 5, "=(SUM A1 A1)");
    worksheetBuilder.createCell(1, 6, "=(SUM A1 A3)");
    worksheetBuilder.createCell(1, 7, "=(SUM A4 A3)");
    worksheetBuilder.createCell(1, 8, "=(SUM A4 A2)");
    worksheetBuilder.createCell(1, 9, "=(SUM A5 A5)");
    //worksheetBuilder.createCell(1, 1, "2");
    Spreadsheet ss = worksheetBuilder.createWorksheet();
    assertEquals(new DoubleValue(1.0), ss.getCellAt(1,1).getValue());
    assertEquals( new DoubleValue(2.0), ss.getCellAt(1, 2).getValue());
    assertEquals(new DoubleValue(2.0), ss.getCellAt(1, 5).getValue());
    assertEquals(new DoubleValue(1.0), ss.getCellAt(1, 6).getValue());
    assertEquals(new DoubleValue(0.0), ss.getCellAt(1, 7).getValue());
    assertEquals(new DoubleValue(2.0), ss.getCellAt(1, 8).getValue());
    assertEquals(new DoubleValue(4.0), ss.getCellAt(1, 9).getValue());
  }


  @Test
  public void testBuilderCyclicReference() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "1");
    worksheetBuilder.createCell(1, 1, "=(SUM A1 1)");
    Spreadsheet ss = worksheetBuilder.createWorksheet();
    ArrayList<String> errors = new ArrayList<>();
    errors.add("The cell A1 depends on a cycle.");
    assertEquals(errors, ss.getErrors());
    assertEquals(new DoubleValue(0.0), ss.getCellAt(1, 1).getValue());
    System.out.println(ss.getCellAt(1, 1).getFormula());
  }

  @Test
  public void testBuilderCyclicReference4() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "A2");
    worksheetBuilder.createCell(1, 2, "=(SUM A3 1)");
    worksheetBuilder.createCell(1, 3, "=(SUM A1 1)");
    Spreadsheet ss = worksheetBuilder.createWorksheet();
    ArrayList<String> errors = new ArrayList<>();
    errors.add("The cell A2 depends on a cycle.");
    errors.add("The cell A1 depends on a cycle.");
    errors.add("The cell A3 depends on a cycle.");
    System.out.print(ss.getCellAt(1, 3).dependsOnCycle());
    //assertEquals(errors, ss.getErrors());
    System.out.println(ss.getCellAt(1, 1).getFormula());
  }


  @Test
  public void testBuilderCyclicReference2() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "A1");
    Spreadsheet ss = worksheetBuilder.createWorksheet();
    assertEquals( new DoubleValue(0.0), ss.getCellAt(1, 1).evaluateCell());
    ArrayList<String> errors = new ArrayList<>();
    System.out.print(ss.getCellAt(1, 1).dependsOnCycle());
    errors.add("The cell A1 depends on a cycle.");
    assertEquals(errors, ss.getErrors());
  }

  @Test
  public void testBuilderCyclicReference3() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 3, "2");
    worksheetBuilder.createCell(1, 1, "A2");
    worksheetBuilder.createCell(1, 2, "A3");
    worksheetBuilder.createCell(1, 3, "A1");

    Spreadsheet ss = worksheetBuilder.createWorksheet();
    System.out.println(ss.getCellAt(1, 1).getValue());
    System.out.println(ss.getCellAt(1, 3).getValue());

    assertEquals(new DoubleValue(0.0), ss.getCellAt(1, 1).evaluateCell());
    ArrayList<String> errors = new ArrayList<>();
    //errors.add("The cell A2 depends on a cycle.");
    //errors.add("The cell A1 depends on a cycle.");
    errors.add("The cell A3 depends on a cycle.");
    //assertEquals(ss.getErrors(), errors);
  }

  @Test
  public void testCreateEmptySpreadsheet() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    Spreadsheet ss = worksheetBuilder.createWorksheet();
    assertEquals(new HashMap<>(), ss.getWorksheet());
  }

  //TODO A test checking for indirect cyclic references.
  //TODO A test for checking blank cells....
  //TODO A test for when we overwrite a cell

}

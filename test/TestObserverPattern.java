import static junit.framework.TestCase.assertEquals;

import edu.cs3500.spreadsheets.model.DoubleValue;
import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.ISpreadsheet;
import edu.cs3500.spreadsheets.model.SSWorksheetBuilder;
import edu.cs3500.spreadsheets.model.Spreadsheet;
import java.util.ArrayList;
import org.junit.Test;

public class TestObserverPattern {

  @Test
  public void testUpdateCellAndObservers() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "1");
    worksheetBuilder.createCell(1, 2, "=(SUM A1 1)");
    worksheetBuilder.createCell(1, 3, "true");
    worksheetBuilder.createCell(1, 4, "\"hello\"");
    worksheetBuilder.createCell(1, 5, "=(SUM A1 A1)");
    ISpreadsheet<ICell> ss = worksheetBuilder.createWorksheet();

    assertEquals(ss.getCellAt(1, 2).getValue(), new DoubleValue(2.0));
    assertEquals(ss.getCellAt(1, 2).getValue(), new DoubleValue(2.0));

    ArrayList<ICell> observers = new ArrayList<>();
    observers.add(ss.getCellAt(1, 2));
    observers.add(ss.getCellAt(1, 5));
    observers.add(ss.getCellAt(1, 5));

    ss.createCellAt(1, 1, "2");
    assertEquals(ss.getCellAt(1, 2).getValue(), new DoubleValue(3.0));
    assertEquals(ss.getCellAt(1, 5).getValue(), new DoubleValue(4.0));


    ss.updateCellAt(1, 1, "3");
    assertEquals(ss.getCellAt(1, 2).getValue(), new DoubleValue(4.0));
    assertEquals(ss.getCellAt(1, 5).getValue(), new DoubleValue(6.0));

  }

  @Test
  public void test2() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "1");
    worksheetBuilder.createCell(1, 1, "=(SUM A1 A1)");
    Spreadsheet ss = worksheetBuilder.createWorksheet();

    assertEquals(ss.getCellAt(1, 1).getValue(), new DoubleValue(0.0));
    assertEquals(ss.getCellAt(1, 1).dependsOnCycle(), true);
    ArrayList<String> errors = new ArrayList<>();
    errors.add("The cell A1 depends on a cycle.");
    assertEquals(ss.getErrors(), errors);
  }

  @Test
  public void test3() {
    SSWorksheetBuilder worksheetBuilder = new SSWorksheetBuilder();
    worksheetBuilder.createCell(1, 1, "1");
    worksheetBuilder.createCell(1, 1, "=(SUM A1 A1)");
    Spreadsheet ss = worksheetBuilder.createWorksheet();

    assertEquals(ss.getCellAt(1, 1).getValue(), new DoubleValue(0.0));
    assertEquals(ss.getCellAt(1, 1).dependsOnCycle(), true);
    ArrayList<String> errors = new ArrayList<>();
    errors.add("The cell A1 depends on a cycle.");
    assertEquals(ss.getErrors(), errors);

    System.out.println(ss.getCellAt(1, 1).dependsOnCycle());
    System.out.println(ss.getCellAt(1, 1).dependsOnCycle());
    System.out.println(ss.getCellAt(1,1).getValue());
    System.out.println(ss.getErrors());
  }

}

import static junit.framework.TestCase.assertEquals;

import edu.cs3500.spreadsheets.model.BooleanValue;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.DoubleValue;
import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.ISpreadsheet;
import edu.cs3500.spreadsheets.model.SSWorksheetBuilder;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import org.junit.Test;

/**
 * Tests ensuring that a file can be read in and handled properly.
 */
public class BeyondGoodTests {

  @Test
  public void testWSBuilder() {
    SSWorksheetBuilder wsBuilder = new SSWorksheetBuilder();
    wsBuilder.createCell(1, 1, "2");
    wsBuilder.createCell(1, 2, "=(SUM 1 2)");
    wsBuilder.createCell(1, 3, "= A1");
    ISpreadsheet<ICell> spreadsheet = wsBuilder.createWorksheet();
    System.out.println(spreadsheet.getCellAt(1, 2));
    assertEquals(spreadsheet.getCellAt(1, 1).getValue().toString(),
            new DoubleValue(2.0).toString());
    ArrayList<Formula> argsList = new ArrayList<>();
    argsList.add(new DoubleValue(3.0));
    argsList.add(new DoubleValue(3.0));
    assertEquals(spreadsheet.getCellAt(1, 2).getValue().toString(),
            new DoubleValue(3.0).toString());
    assertEquals(spreadsheet.getCellAt(1, 3).getValue().toString(),
            new DoubleValue(2.0).toString());
  }

  @Test
  public void testInputFile1() {
    WorksheetReader wsReader = new WorksheetReader();
    SSWorksheetBuilder wsBuilder = new SSWorksheetBuilder();
    FileReader fr;
    try {
      fr = new FileReader("/Users/soniapopovic/Desktop/ExcelExampleInput.txt");
    } catch (FileNotFoundException fe) {
      throw new IllegalArgumentException("This is not a valid file.");
    }
    ISpreadsheet<ICell> spreadsheet = wsReader.read(wsBuilder, fr);
    Coord b1 = new Coord(Coord.colNameToIndex("B"), 1);
    Coord a2 = new Coord(Coord.colNameToIndex("A"), 2);
    Coord a3 =  new Coord(Coord.colNameToIndex("A"), 3);
    Coord a4 = new Coord(Coord.colNameToIndex("A"), 4);
    Coord a5 = new Coord(1, 5);

    assertEquals(spreadsheet.getCellAt(b1.col, b1.row).getValue(),
            new DoubleValue(4.0));
    assertEquals(spreadsheet.getCellAt(a2.col, a2.row).getValue(),
            new BooleanValue(false));
    assertEquals(spreadsheet.getCellAt(a3.col, a3.row).getValue(),
            new DoubleValue(8.0));
    assertEquals(spreadsheet.getCellAt(a4.col, a4.row).getValue(),
            new DoubleValue(54.0));
  }
  @Test
  public void testCyclicData() {
    WorksheetReader wsReader = new WorksheetReader();
    SSWorksheetBuilder wsBuilder = new SSWorksheetBuilder();
    FileReader fr;
    try {
      fr = new FileReader("/Users/soniapopovic/Documents/AcyclicData.txt");
    } catch (FileNotFoundException fe) {
      throw new IllegalArgumentException("This is not a valid file.");
    }
    ISpreadsheet spreadsheet = wsReader.read(wsBuilder, fr);
  }


  @Test
  public void testNotCyclicData() {
    WorksheetReader wsReader = new WorksheetReader();
    SSWorksheetBuilder wsBuilder = new SSWorksheetBuilder();
    FileReader fr;
    try {
      fr = new FileReader("/Users/soniapopovic/Desktop/ExcelExampleInput.txt");
    } catch (FileNotFoundException fe) {
      throw new IllegalArgumentException("This is not a valid file.");
    }
    ISpreadsheet<ICell> spreadsheet = wsReader.read(wsBuilder, fr);
    Coord a2 = new Coord(Coord.colNameToIndex("A"),2);
    assertEquals(spreadsheet.getCellAt(a2.col, a2.row).getValue(),
            new BooleanValue(false));
  }

}

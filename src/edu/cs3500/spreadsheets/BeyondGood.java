package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.model.BooleanValue;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.DoubleValue;
import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.ISpreadsheet;
import edu.cs3500.spreadsheets.model.SSWorksheetBuilder;
import edu.cs3500.spreadsheets.model.Value;
import edu.cs3500.spreadsheets.model.WorksheetReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   *
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /*
      TODO: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */
    if (args.length == 4) {
      Value val = BeyondGood.getEvaluated(args);
      System.out.print(val.toString());

    }
  }

  private static Value getEvaluated(String[] args) {
    WorksheetReader wsReader = new WorksheetReader();
    SSWorksheetBuilder wsBuilder = new SSWorksheetBuilder();
    if (args[0].equalsIgnoreCase("-in")) {
      FileReader fr;
      try {
        fr = new FileReader(args[1]);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("The inputted file is not a valid file.");
      }
      ISpreadsheet<ICell> spreadsheet = wsReader.read(wsBuilder, fr);
      if (args[2].equalsIgnoreCase("-eval")) {
        String coord = args[3];
        Coord c = BeyondGood.stringToCoord(coord);
        return spreadsheet.getCellAt(c.col, c.row).getValue();
      } else {
        throw new IllegalArgumentException("Not a valid input to be evaluated.");
      }
    } else {
      throw new IllegalArgumentException("Not a valid input");
    }


  }

  private static Coord stringToCoord(String coordString) {
    int i = 0;
    while (Character.isLetter(coordString.charAt(i))) {
      i++;
    }
    String col = coordString.substring(0, i);
    int row = Integer.valueOf(coordString.substring(i));
    return new Coord(Coord.colNameToIndex(col), row);
  }

}

package cs3500.pa03;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    View view = new View(new PrintStream(System.out));
    Readable readable = new InputStreamReader(System.in);
    Controller controller = new Controller(readable, view);

    try {
      controller.run();
    } catch (IOException e) {
      System.out.println("An unexpected error occured!");
    }
  }
}
package cs3500.pa03;

import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/**
 * tests for view
 */
public class ViewTest {

  /**
   * testing view
   */
  @Test
  public void testView() {
    Appendable appendable = new PrintStream(System.out);

    View view = new View(appendable);

  }
}

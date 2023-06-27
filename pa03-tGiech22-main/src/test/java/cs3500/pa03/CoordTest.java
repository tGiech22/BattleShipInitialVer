package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * tests for Coord class
 */
public class CoordTest {

  /**
   * testing coord
   */
  @Test
  public void testCoord() {
    Coord coord = new Coord(1, 2);

    assertEquals(coord.getX(), 1);
    assertEquals(coord.getY(), 2);
  }

}

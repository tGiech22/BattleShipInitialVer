package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * tests for ship class
 */
public class ShipTest {

  /**
   * testing ship
   */
  @Test
  public void testShip() {
    List<Coord> posOfShip = new ArrayList<>();
    posOfShip.add(new Coord(1, 0));
    posOfShip.add(new Coord(1, 1));
    posOfShip.add(new Coord(1, 2));

    Ship testShip1 = new Ship(posOfShip, ShipType.SUBMARINE);

    assertEquals(testShip1.getLocation().get(0).getX(), 1);
    assertEquals(testShip1.getLocation().get(0).getY(), 0);
    assertEquals(testShip1.getType(), ShipType.SUBMARINE);
    assertEquals(testShip1.getType().getSize(), 3);
  }


}

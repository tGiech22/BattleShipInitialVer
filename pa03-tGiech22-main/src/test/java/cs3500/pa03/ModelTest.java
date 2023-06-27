package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * testing model
 */
public class ModelTest {

  /**
   * testing model
   */
  @Test
  public void testModel() {
    Model model = new Model(new Board(6, 6), new Random(2));
    assertEquals(model.getBoardRep().length, 6);
    assertEquals(model.getBoardRep()[0].length, 6);

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

    model.generateFleet(specifications);

    assertEquals(model.getFleet().size(), 4);
    assertEquals(model.getShipPos().size(), 18);

    Model model2 = new Model(new Board(10, 6), new Random());
    model2.initializeBoardRep();

    List<Coord> posOfShip1 = new ArrayList<>();
    posOfShip1.add(new Coord(1, 0));
    posOfShip1.add(new Coord(1, 1));
    posOfShip1.add(new Coord(1, 2));

    Ship testShip1 = new Ship(posOfShip1, ShipType.SUBMARINE);

    model2.placeShip(testShip1);
    assertEquals(model2.getBoardRep()[0][0], '-');
    assertEquals(model2.getBoardRep()[0][1], 'S');

    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 0));
    shots.add(new Coord(2, 2));

    model2.updateBoardRep(shots, model2.getBoardRep());
    assertEquals(model2.getBoardRep()[2][2], 'M');
    assertEquals(model2.getBoardRep()[0][1], 'H');

    List<Coord> posOfShip2 = new ArrayList<>();
    posOfShip2.add(new Coord(9, 0));
    posOfShip2.add(new Coord(10, 0));
    posOfShip2.add(new Coord(11, 0));

    Ship testShip2 = new Ship(posOfShip2, ShipType.SUBMARINE);

    //model2.placeShip(testShip2);


  }
}

package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * tests for computerplayer class
 */
public class CompPlayerTest {

  /**
   * testing compPlayer class
   */
  @Test
  public void testCompPlayer() {
    ComputerPlayer player1 = new ComputerPlayer ( 6, 6, new Random(15));
    assertEquals(player1.getHeight(), 6);
    assertEquals(player1.getWidth(), 6);
    assertEquals(player1.name(), "Computer");

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

    List<Ship> fleet = player1.setup(6, 6, specifications);
    assertEquals(fleet.size(), 4);
    assertEquals(player1.getBoardRep().length, 6);

    player1.endGame(GameResult.WIN, "You won!");

    assertEquals(player1.getResult(), GameResult.WIN);

    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        shots.add(new Coord(j, i));
      }
    }

    assertEquals(player1.reportDamage(shots).size(), 18);
    //assertEquals(player1.checkShipsSunk(player1.reportDamage(shots)).size(), 0);

  }
}

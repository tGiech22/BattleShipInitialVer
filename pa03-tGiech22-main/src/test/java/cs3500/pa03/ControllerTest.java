package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * tests for Controller
 */
public class ControllerTest {

  /**
   * testing controller
   *
   */
  @Test
  public void testController() {
    StringBuilder output = new StringBuilder();
    Readable readable = new InputStreamReader(System.in);
    View view = new View(output);

    Controller controller = new Controller(readable, view);

    assertEquals(controller.getView(), view);
    assertEquals(controller.getReadable(), readable);

    String testInput  = "6 6\n";
    StringReader input = new StringReader(testInput);
    Controller controller2 = new Controller(input, view);

    String s = controller2.welcome();
    assertEquals(s, "6 6");

    testInput = "1 1 1 1\n";
    StringReader input2 = new StringReader(testInput);
    Controller controller3 = new Controller(input2, view);
    s = controller3.getFleet(6, 6);
    assertEquals(s, "1 1 1 1");

    ArrayList<Integer> shipNums = controller3.parseThroughFleetDistribution("1 1 1 1");
    assertEquals(shipNums.get(0), 1);

    Map<ShipType, Integer> specifications = controller3.distributeShips(shipNums);
    assertEquals(specifications.get(ShipType.SUBMARINE), 1);

    HumanPlayer humanPlayer = new HumanPlayer("Tony", 6, 6, new Random(15));
    ComputerPlayer computerPlayer = new ComputerPlayer(6, 6, new Random(15));
    humanPlayer.setup(6, 6, specifications);
    computerPlayer.setup(6, 6, specifications);

    controller3.showBoards(computerPlayer, humanPlayer);

    assertEquals("Hello! Welcome to the OOD BattleSalvo Game! \n"
        + "Please enter a valid height and width below: \n"
        + "------------------------------------------------------------------\n"
        + "------------------------------------------------------------------\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, you MUST have 1 of each and your fleet may not exceed size 6\n"
        + "------------------------------------------------------------------\n"
        + "oppenent's board: \n"
        + "- - S - - S \n"
        + "- - S - - S \n"
        + "- - S - - S \n"
        + "- - S - - - \n"
        + "S S S S S S \n"
        + "- - S - - - \n"
        + "your board: \n"
        + "- - S - - S \n"
        + "- - S - - S \n"
        + "- - S - - S \n"
        + "- - S - - - \n"
        + "S S S S S S \n"
        + "- - S - - - \n", output.toString());

    /*
    String testInput  = "6 6\n" + "1 1 1 1" + "0 0\r2 0\r3 0\r4 0" + "5 0\r0 1\r1 1\r1 2";
    StringReader input = new StringReader(testInput);
    Controller controller2 = new Controller(input, view);

    try{
      controller2.run();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals("", output.toString());*/
  }
}

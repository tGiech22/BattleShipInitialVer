package cs3500.pa03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * represents a controller
 */
public class Controller {
  private Readable readable;
  private View view;
  private static Scanner scannerObj;

  /**
   * constructor
   *
   * @param readable for reading input
   * @param view for outputting
   */
  Controller(Readable readable, View view) {
    this.readable = readable;
    this.view = view;
    this.scannerObj = new Scanner(readable);
  }

  /**
   * runs the program
   *
   * @throws IOException that will be handled
   */
  public void run() throws IOException {
    int width = 0;
    int height = 0;

    // prints welcome screen and asks for board width and height
    String widthAndHeight = welcome();

    width = Integer.parseInt(widthAndHeight.substring(0, widthAndHeight.indexOf(" ")));
    height = Integer.parseInt(widthAndHeight.substring(widthAndHeight.indexOf(" ") + 1));

    String distributionOfShips = getFleet(width, height);
    ArrayList<Integer> shipNums = parseThroughFleetDistribution(distributionOfShips);

    Map<ShipType, Integer> specifications = distributeShips(shipNums);

    HumanPlayer humanPlayer = new HumanPlayer("Tony", width, height, new Random());
    List<Ship> humanFleet = humanPlayer.setup(height, width, specifications);

    ComputerPlayer compPlayer = new ComputerPlayer(width, height, new Random());
    List<Ship> compFleet = compPlayer.setup(height, width, specifications);

    showBoards(compPlayer, humanPlayer);

    List<Coord> shotsTakenHuman = new ArrayList<>();
    List<Coord> shotsTakenComp = new ArrayList<>();

    // game loop
    while (humanFleet.size() > 0 && compFleet.size() > 0) {
      View.printMessage("Please type in " + humanFleet.size() + " shots in the format [x y]:\n");

      shotsTakenHuman = humanPlayer.takeShots();
      shotsTakenComp = compPlayer.takeShots();

      List<Coord> compDamagedLocations = compPlayer.reportDamage(shotsTakenHuman);
      compFleet = compPlayer.checkShipsSunk(compDamagedLocations);
      List<Coord> humanDamagedLocations = humanPlayer.reportDamage(shotsTakenComp);
      humanFleet = humanPlayer.checkShipsSunk(humanDamagedLocations);

      view.printMessage("These locations were hit: \n");
      for (Coord coord : humanDamagedLocations) {
        view.printMessage("[" + coord.getX() + ", " + coord.getY() + "]");
      }

      showBoards(compPlayer, humanPlayer);
    }

    // ending
    if (humanFleet.size() == 0 && compFleet.size() > 0) {
      humanPlayer.endGame(GameResult.LOSS, "Sorry, you lost.");
    } else if (humanFleet.size() > 0 && compFleet.size() > 0) {
      humanPlayer.endGame(GameResult.WIN, "Congratulations! You won!");
    } else if (humanFleet.size() == 0 && compFleet.size() == 0) {
      humanPlayer.endGame(GameResult.TIE, "It's a tie.");
    }

  }

  /**
   * prints welcome screen and asks for board width and height
   *
   * @return string of board width and height
   */
  public String welcome() {
    try {
      view.printMessage("Hello! Welcome to the OOD BattleSalvo Game! \n");
      view.printMessage("Please enter a valid height and width below: \n");
      view.printMessage("------------------------------------------------------------------\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

    String widthAndHeight = getUserInput();

    return widthAndHeight;
  }

  /**
   * asks for number of each type of ship
   *
   * @param width of board
   * @param height of board
   *
   * @return distribution of each type of ship
   */
  public String getFleet(int width, int height) {
    try {
      view.printMessage("------------------------------------------------------------------\n");
      view.printMessage("Please enter your fleet in the order "
          + "[Carrier, Battleship, Destroyer, Submarine].\n"
          + "Remember, you MUST have 1 of each "
          + "and your fleet may not exceed size " + Math.min(width, height) + "\n");
      view.printMessage("------------------------------------------------------------------\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

    String distributionOfShips = getUserInput();

    return distributionOfShips;
  }

  /**
   * parses through the string got from the user to determine how many of each type of ship there
   * should be
   *
   * @param distributionOfShips for each type
   *
   * @return an array list with the number for each type of ship
   */
  public ArrayList<Integer> parseThroughFleetDistribution(String distributionOfShips) {
    String num = "";
    ArrayList<Integer> shipNums = new ArrayList<>();
    for (int i = 0; i < distributionOfShips.length(); i++) {
      if (distributionOfShips.charAt(i) >= '0' && distributionOfShips.charAt(i) <= '9') {
        num += distributionOfShips.charAt(i);
      } else {
        shipNums.add(Integer.parseInt(num));
        num = "";
      }
    }

    shipNums.add(Integer.parseInt(num));

    return shipNums;
  }

  /**
   * actually distributes the number of ships among the types and returns them in a map
   *
   * @param shipNums the number of each type of ship
   *
   * @return a map with each type having their corresponding number of occurrences
   */
  public Map<ShipType, Integer> distributeShips(ArrayList<Integer> shipNums) {
    int carrierNum = shipNums.get(0);
    int battleshipNum = shipNums.get(1);
    int destroyerNum = shipNums.get(2);
    int submarineNum = shipNums.get(3);

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, carrierNum);
    specifications.put(ShipType.BATTLESHIP, battleshipNum);
    specifications.put(ShipType.DESTROYER, destroyerNum);
    specifications.put(ShipType.SUBMARINE, submarineNum);

    return specifications;
  }

  /**
   * displays both player's boards
   *
   * @param compPlayer the computer player
   * @param humanPlayer the human player
   */
  public void showBoards(ComputerPlayer compPlayer, HumanPlayer humanPlayer) {
    try {
      view.printMessage("oppenent's board: \n");
      view.displayBoard(compPlayer.boardRep);

      view.printMessage("your board: \n");
      view.displayBoard(humanPlayer.getBoardRep());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * gets user input
   *
   * @return user input as a string
   */
  public static String getUserInput() {
    return scannerObj.nextLine();
  }

  /**
   * getter for readable
   *
   * @return a readable
   */
  public Readable getReadable() {
    return this.readable;
  }

  /**
   * getter for view
   *
   * @return a view
   */
  public View getView() {
    return this.view;
  }
}

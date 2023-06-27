package cs3500.pa03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * represents the human player
 */
public class HumanPlayer implements Player {

  private String name;
  private int width;
  private int height;
  private Board gameBoard;
  private Model model;
  private List<Ship> fleet;
  private List<Coord> allPosOfFleet;
  private char[][] boardRep;
  private GameResult result;

  /**
   * constructor
   *
   * @param name of the human player
   * @param width of the board
   * @param height of the board
   */
  HumanPlayer(String name, int width, int height, Random rand) {
    this.width = width;
    this.height = height;
    this.name = name;
    gameBoard = new Board(this.width, this.height);
    model = new Model(gameBoard, rand);
    boardRep = new char[this.height][this.width];
    allPosOfFleet = new ArrayList<>();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    model.initializeBoardRep();

    model.generateFleet(specifications);

    this.fleet = model.getFleet();
    this.allPosOfFleet = model.getShipPos();

    for (Ship ship : fleet) {
      model.placeShip(ship);
    }

    this.boardRep = model.getBoardRep();

    return fleet;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shotsTaken = new ArrayList<>();
    for (int i = 0; i < fleet.size(); i++) {
      String shot = Controller.getUserInput();
      int x = Integer.parseInt(shot.substring(0, shot.indexOf(" ")));
      int y = Integer.parseInt(shot.substring(shot.indexOf(" ") + 1));

      shotsTaken.add(new Coord(x, y));
    }

    return shotsTaken;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> damagedLocations = new ArrayList<>();

    for (int i = 0; i < opponentShotsOnBoard.size(); i++) {
      for (int j = 0; j < allPosOfFleet.size(); j++) {
        if (opponentShotsOnBoard.get(i).getX() == allPosOfFleet.get(j).getX()
            && opponentShotsOnBoard.get(i).getY() == allPosOfFleet.get(j).getY()) {
          damagedLocations.add(opponentShotsOnBoard.get(i));
        }
      }
    }

    boardRep = model.updateBoardRep(opponentShotsOnBoard, this.boardRep);

    return damagedLocations;
  }

  /**
   * check if this fleet has any ships that are sunk based on the list of coords given
   *
   * @param damagedLocations list of places where ships were hit
   *
   * @return list of ships sunks
   */
  public List<Ship> checkShipsSunk(List<Coord> damagedLocations) {
    Iterator<Ship> itr = fleet.iterator();

    while (itr.hasNext()) {
      if (checkSingleShip(damagedLocations, itr.next())) {
        itr.remove();
      }
    }

    return fleet;
  }

  /**
   * checks to see if a single ship is sunk based on the list of damaged locations
   *
   * @param damagedLocations places where ships were hit
   * @param ship the ship that is being checked to see if it is sunk
   * @return a boolean for whether or not the given ship is sunk
   */
  public boolean checkSingleShip(List<Coord> damagedLocations, Ship ship) {
    boolean isSunk = false;
    List<Coord> location = ship.getLocation();
    int count = 0;

    for (int i = 0; i < location.size(); i++) {
      for (int j = 0; j < damagedLocations.size(); j++) {
        if (location.get(i).getX() == damagedLocations.get(j).getX()
            && location.get(i).getY() == damagedLocations.get(j).getY()) {
          count++;
        }
      }

      if (count == ship.getType().getSize()) {
        isSunk = true;
        count = 0;
      }
    }

    return isSunk;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) throws IOException {
    View.printMessage("These are the shots that were successful: \n");
    for (Coord coord : shotsThatHitOpponentShips) {
      View.printMessage("[" + coord.getX() + ", " + coord.getY() + "]\n");
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) throws IOException {
    this.result = result;
  }

  /**
   * getter for GameResult result
   *
   * @return a game result
   */
  public GameResult getResult() {
    return this.result;
  }

  /**
   * getter for char[][] boardRep
   *
   * @return a char[][] representing the board
   */
  public char[][] getBoardRep() {
    return this.boardRep;
  }

  /**
   * getter for int width
   *
   * @return the width
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * getter for int height
   *
   * @return the height
   */
  public int getHeight() {
    return this.height;
  }
}
